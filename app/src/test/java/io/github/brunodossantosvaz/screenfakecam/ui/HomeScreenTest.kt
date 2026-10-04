package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
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
}
