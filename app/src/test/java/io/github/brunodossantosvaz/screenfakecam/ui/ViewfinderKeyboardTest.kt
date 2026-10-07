package io.github.brunodossantosvaz.screenfakecam.ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.withKeyDown
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Regression for #70: a focused viewfinder must work without touch gestures (DESIGN.md, FE-04). */
@RunWith(AndroidJUnit4::class)
class ViewfinderKeyboardTest {
    @get:Rule
    val compose = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val description get() = context.getString(R.string.viewfinder_description)
    private val framing = mutableStateOf(Framing(1000, 800, 500, 400).withZoom(2f))

    private fun show() {
        lateinit var inputMode: InputModeManager
        compose.setContent {
            inputMode = LocalInputModeManager.current
            ScreenFakeCamTheme {
                ViewfinderScreen(
                    image = ImageBitmap(1000, 800),
                    framing = framing.value,
                    onViewSize = { _, _ -> },
                    onZoomTo = { framing.value = framing.value.withZoom(it) },
                    onZoomBy = { framing.value = framing.value.withZoom(framing.value.zoom * it) },
                    onDrag = { dx, dy -> framing.value = framing.value.draggedBy(dx, dy) },
                    onBack = {},
                )
            }
        }
        compose.runOnIdle { inputMode.requestInputMode(InputMode.Keyboard) }
        compose.onNodeWithContentDescription(description).performSemanticsAction(SemanticsActions.RequestFocus) { it() }
    }

    private fun press(key: Key) {
        compose.onNodeWithContentDescription(description).performKeyInput { pressKey(key) }
        compose.waitForIdle()
    }

    @Test
    fun focusedZoomButtonDoesNotSendFramingShortcutsToTheViewfinder() {
        show()
        val initial = framing.value
        val zoomButton = compose.onNodeWithText(context.getString(R.string.zoom_step, 2))
        zoomButton.performSemanticsAction(SemanticsActions.RequestFocus) { it() }
        zoomButton.assertIsFocused()
        compose.onNodeWithContentDescription(description).assertIsNotFocused()
        zoomButton.performKeyInput { pressKey(Key.Plus) }
        compose.waitForIdle()
        assertEquals(initial, framing.value)
        zoomButton.performKeyInput { pressKey(Key.Minus) }
        compose.waitForIdle()
        assertEquals(initial, framing.value)
        zoomButton.performKeyInput { pressKey(Key.DirectionRight) }
        compose.waitForIdle()
        assertEquals(initial, framing.value)
    }

    @Test
    fun directionKeysMoveTheImageInEveryDirection() {
        show()
        press(Key.DirectionLeft)
        assertTrue(framing.value.offsetX < 0f)
        press(Key.DirectionRight)
        assertEquals(0f, framing.value.offsetX, 0f)
        press(Key.DirectionUp)
        assertTrue(framing.value.offsetY < 0f)
        press(Key.DirectionDown)
        assertEquals(0f, framing.value.offsetY, 0f)
    }

    @Test
    fun plusAndMinusChangeZoomAndKeepTheLimits() {
        show()
        press(Key.Plus)
        assertEquals(4f, framing.value.zoom)
        press(Key.Plus)
        assertEquals(4f, framing.value.zoom)
        press(Key.Minus)
        assertEquals(2f, framing.value.zoom)
        press(Key.Minus)
        assertEquals(1f, framing.value.zoom)
        press(Key.Minus)
        assertEquals(1f, framing.value.zoom)
    }

    @Test
    fun shiftedEqualsAndNumberPadKeysAlsoChangeZoom() {
        show()
        compose.onNodeWithContentDescription(description).performKeyInput {
            withKeyDown(Key.ShiftLeft) { pressKey(Key.Equals) }
        }
        compose.waitForIdle()
        assertEquals(4f, framing.value.zoom)
        press(Key.NumPadSubtract)
        assertEquals(2f, framing.value.zoom)
        press(Key.NumPadAdd)
        assertEquals(4f, framing.value.zoom)
    }

    @Test
    fun draggingAtOneTimesNeverMovesTheImageOutsideItsBounds() {
        framing.value = framing.value.withZoom(1f)
        show()
        press(Key.DirectionLeft)
        press(Key.DirectionUp)
        assertEquals(0f, framing.value.offsetX, 0f)
        assertEquals(0f, framing.value.offsetY, 0f)
    }

    @Test
    fun keyReleaseAndUnrelatedKeysDoNotChangeTheFraming() {
        show()
        val initial = framing.value
        compose.onNodeWithContentDescription(description).performKeyInput {
            keyDown(Key.DirectionRight)
        }
        compose.waitForIdle()
        val moved = framing.value
        assertTrue(moved.offsetX > initial.offsetX)
        compose.onNodeWithContentDescription(description).performKeyInput {
            keyUp(Key.DirectionRight)
            pressKey(Key.A)
            pressKey(Key.Equals)
        }
        compose.waitForIdle()
        assertEquals(moved, framing.value)
    }
}
