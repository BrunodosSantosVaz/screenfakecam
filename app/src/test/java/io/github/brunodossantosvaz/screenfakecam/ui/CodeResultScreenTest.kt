package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextLayoutResult
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.application.DecodedCode
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class CodeResultScreenTest {
    @get:Rule
    val compose = createComposeRule()

    private val actions = mutableListOf<String>()

    private fun show(outcome: CodeOutcome) =
        compose.setContent {
            ScreenFakeCamTheme {
                CodeResultScreen(
                    outcome = outcome,
                    onCopy = { actions += "copiar $it" },
                    onOpenLink = { actions += "abrir $it" },
                    onShare = { actions += "compartilhar $it" },
                    onReadAnother = { actions += "outra" },
                    onFrameInViewfinder = { actions += "visor" },
                    onBack = { actions += "voltar" },
                )
            }
        }

    @Test
    fun aLinkShowsTypeTextAndTheThreeActions() {
        show(CodeOutcome.Found(DecodedCode("https://exemplo.com/cardapio", "QR_CODE")))
        compose.onNodeWithText("QR code").assertIsDisplayed()
        compose.onNodeWithText("https://exemplo.com/cardapio").assertIsDisplayed()
        compose.onNodeWithText("Copiar").performClick()
        compose.onNodeWithText("Abrir link").performClick()
        compose.onNodeWithText("Compartilhar").performClick()
        assertEquals(
            listOf(
                "copiar https://exemplo.com/cardapio",
                "abrir https://exemplo.com/cardapio",
                "compartilhar https://exemplo.com/cardapio",
            ),
            actions,
        )
    }

    private fun layout(label: String): TextLayoutResult {
        val node = compose.onNodeWithText(label, useUnmergedTree = true).fetchSemanticsNode()
        val layouts = mutableListOf<TextLayoutResult>()
        node.config[SemanticsActions.GetTextLayoutResult].action?.invoke(layouts)
        return layouts.single()
    }

    // Bug #61: on a narrow phone, "Compartilhar" wrapped as "Compartil / har" and the button grew taller.
    @Test
    @Config(qualifiers = "w360dp-h780dp")
    fun theActionLabelsFitInOneLineOnA360dpScreen() {
        show(CodeOutcome.Found(DecodedCode("https://exemplo.com/cardapio", "QR_CODE")))
        for (label in listOf("Copiar", "Abrir link", "Compartilhar")) {
            val text = layout(label)
            assertEquals(label, 1, text.lineCount)
            assertFalse("$label cortado", text.multiParagraph.didExceedMaxLines)
        }
    }

    @Test
    fun aTextThatIsNotAWebLinkHasNoOpenButton() {
        show(CodeOutcome.Found(DecodedCode("intent://x#Intent;end", "QR_CODE")))
        compose.onNodeWithText("Abrir link").assertDoesNotExist()
    }

    @Test
    fun notFoundOffersAnotherImageOrTheViewfinder() {
        show(CodeOutcome.NotFound)
        compose.onNodeWithText("Escolher outra").performClick()
        compose.onNodeWithText("Enquadrar no visor").performClick()
        assertEquals(listOf("outra", "visor"), actions)
    }

    @Test
    fun formatNamesAreReadable() {
        assertEquals("EAN-13", formatName("EAN_13"))
        assertEquals("Code 128", formatName("CODE_128"))
    }
}
