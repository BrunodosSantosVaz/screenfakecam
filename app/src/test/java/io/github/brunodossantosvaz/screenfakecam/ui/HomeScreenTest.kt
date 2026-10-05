package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun showsThePrivacyPromise() {
        compose.setContent { ScreenFakeCamTheme { HomeScreen() } }
        compose.onNodeWithText("Nada sai do seu celular. O app não usa internet.").assertIsDisplayed()
    }

    @Test
    fun chooseImageOpensThePicker() {
        var opened = false
        compose.setContent { ScreenFakeCamTheme { HomeScreen(onChooseImage = { opened = true }) } }
        compose.onNodeWithText("Escolher imagem").performClick()
        assertTrue(opened)
    }

    @Test
    fun explainsAnImageThatCannotBeOpened() {
        compose.setContent { ScreenFakeCamTheme { HomeScreen(failed = true) } }
        compose.onNodeWithText("Não foi possível abrir essa imagem. Escolha outra.").assertIsDisplayed()
    }

    @Test
    fun readCodeStartsTheReader() {
        var asked = false
        compose.setContent { ScreenFakeCamTheme { HomeScreen(onReadCode = { asked = true }) } }
        compose.onNodeWithText("Ler QR ou código de barras").performClick()
        assertTrue(asked)
    }
}
