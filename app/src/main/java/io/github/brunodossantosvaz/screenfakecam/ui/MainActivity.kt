package io.github.brunodossantosvaz.screenfakecam.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import io.github.brunodossantosvaz.screenfakecam.ui.theme.Tokens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ScreenFakeCamTheme { HomeScreen() } }
    }
}

/** Start screen (also the empty state). The actions arrive with the walking skeleton epic. */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Tokens.background).padding(Tokens.space5),
        verticalArrangement = Arrangement.spacedBy(Tokens.space4, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.home_title), style = MaterialTheme.typography.titleLarge, color = Tokens.text)
        Text(
            stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = Tokens.textSecondary,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.home_privacy),
            style = MaterialTheme.typography.labelMedium,
            color = Tokens.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}
