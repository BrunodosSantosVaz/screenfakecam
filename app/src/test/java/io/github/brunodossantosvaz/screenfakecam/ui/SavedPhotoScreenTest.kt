package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SavedPhotoScreenTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun showsWhereItWasSavedAndTheActions() {
        val clicks = mutableListOf<String>()
        compose.setContent {
            ScreenFakeCamTheme {
                SavedPhotoScreen(
                    image = ImageBitmap(40, 30),
                    savedAt = "05/10/2026 10:15",
                    inGallery = true,
                    onShare = { clicks += "compartilhar" },
                    onTakeAnother = { clicks += "outra" },
                    onBack = { clicks += "voltar" },
                )
            }
        }
        compose.onNodeWithText("Salva na galeria em 05/10/2026 10:15").assertIsDisplayed()
        compose.onNodeWithText("Compartilhar").performClick()
        compose.onNodeWithText("Tirar outra").performClick()
        compose.onNodeWithText("Voltar").performClick()
        assertEquals(listOf("compartilhar", "outra", "voltar"), clicks)
    }

    @Test
    fun onAndroidEightOrNineItSaysTheChosenPlace() {
        compose.setContent {
            ScreenFakeCamTheme {
                SavedPhotoScreen(
                    null,
                    "05/10/2026 10:15",
                    inGallery = false,
                    onShare = {},
                    onTakeAnother = {},
                    onBack = {},
                )
            }
        }
        compose.onNodeWithText("Salva no local escolhido em 05/10/2026 10:15").assertIsDisplayed()
    }

    @Test
    fun datesUseTheBrazilianFormat() {
        val date = java.util.GregorianCalendar(2026, java.util.Calendar.OCTOBER, 3, 14, 5).time
        assertEquals("03/10/2026, 14:05", brazilianDateTime(date))
    }
}
