package io.github.brunodossantosvaz.screenfakecam

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import io.github.brunodossantosvaz.screenfakecam.application.PictureLoader
import io.github.brunodossantosvaz.screenfakecam.infrastructure.AndroidPictureLoader

/** Composition root: the only place that knows the infrastructure classes (ui talks to application ports). */
class ScreenFakeCamApp : Application() {
    val pictureLoader: PictureLoader<ImageBitmap> by lazy { AndroidPictureLoader(contentResolver) }
}
