package io.github.brunodossantosvaz.screenfakecam.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.ScreenFakeCamApp
import io.github.brunodossantosvaz.screenfakecam.ui.theme.ScreenFakeCamTheme
import io.github.brunodossantosvaz.screenfakecam.ui.theme.Tokens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loader = (application as ScreenFakeCamApp).pictureLoader
        val maxSide = maxOf(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels) * 2
        setContent {
            ScreenFakeCamTheme {
                val model = viewModel { ViewfinderViewModel(loader) }
                val pick =
                    rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
                        if (uri != null) model.onPicked(uri.toString(), maxSide)
                    }
                val chooseImage = { pick.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
                val picture = model.picture
                if (picture == null) {
                    HomeScreen(onChooseImage = chooseImage, loading = model.loading, failed = model.failed)
                } else {
                    BackHandler(onBack = model::close)
                    ViewfinderScreen(
                        image = picture.image,
                        framing = model.framing,
                        onViewSize = model::onViewSize,
                        onZoomTo = model::zoomTo,
                        onZoomBy = model::zoomBy,
                        onDrag = model::drag,
                        onBack = model::close,
                    )
                }
            }
        }
    }
}

/** Start screen (also the empty state): choose an image from the system photo picker. */
@Composable
fun HomeScreen(
    onChooseImage: () -> Unit = {},
    loading: Boolean = false,
    failed: Boolean = false,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Tokens.background)
                .safeDrawingPadding()
                .padding(Tokens.space5),
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
        if (loading) {
            CircularProgressIndicator(color = Tokens.primary)
            Text(stringResource(R.string.loading_image), color = Tokens.textSecondary)
        } else {
            Button(
                onClick = onChooseImage,
                colors = ButtonDefaults.buttonColors(containerColor = Tokens.primary, contentColor = Tokens.onPrimary),
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = Tokens.minTouch),
            ) {
                Text(stringResource(R.string.choose_image), fontWeight = FontWeight.SemiBold)
            }
        }
        if (failed) Text(stringResource(R.string.image_error), color = Tokens.error, textAlign = TextAlign.Center)
        Text(
            stringResource(R.string.home_privacy),
            style = MaterialTheme.typography.labelMedium,
            color = Tokens.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}
