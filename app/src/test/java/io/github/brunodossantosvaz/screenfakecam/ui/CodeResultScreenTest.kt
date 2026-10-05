package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.application.DecodedCode
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
