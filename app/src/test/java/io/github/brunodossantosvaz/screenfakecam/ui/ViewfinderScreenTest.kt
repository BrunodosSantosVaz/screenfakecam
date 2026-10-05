package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewfinderScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private fun show(
        framing: Framing = Framing(100, 80, 50, 40),
        onZoomTo: (Float) -> Unit = {},
        onBack: () -> Unit = {},
        onShutter: () -> Unit = {},
    ) = compose.setContent {
        ScreenFakeCamTheme {
            ViewfinderScreen(
                image = ImageBitmap(100, 80),
                framing = framing,
                onViewSize = { _, _ -> },
                onZoomTo = onZoomTo,
                onZoomBy = {},
                onDrag = { _, _ -> },
                onBack = onBack,
                onShutter = onShutter,
            )
        }
    }

    @Test
    fun zoomButtonsAskForTheZoom() {
        var asked = 0f
        show(onZoomTo = { asked = it })
        compose.onNodeWithText("2×").performClick()
        assertEquals(2f, asked)
    }

    @Test
    fun theCurrentZoomIsSelected() {
        show(framing = Framing(100, 80, 50, 40).withZoom(4f))
        compose.onNodeWithText("4×").assertIsSelected()
    }

    @Test
    fun backLeavesTheViewfinder() {
        var back = false
        show(onBack = { back = true })
        compose.onNodeWithText("Voltar").performClick()
        assertTrue(back)
    }

    @Test
    fun theShutterTakesThePhoto() {
        var taken = 0
        show(onShutter = { taken++ })
        compose.onNodeWithContentDescription("Tirar foto").performClick()
        assertEquals(1, taken)
    }
}
