package io.github.brunodossantosvaz.screenfakecam

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import io.github.brunodossantosvaz.screenfakecam.application.CodeReader
import io.github.brunodossantosvaz.screenfakecam.application.PhotoStore
import io.github.brunodossantosvaz.screenfakecam.application.PictureLoader
import io.github.brunodossantosvaz.screenfakecam.infrastructure.AndroidPhotoStore
import io.github.brunodossantosvaz.screenfakecam.infrastructure.AndroidPictureLoader
import io.github.brunodossantosvaz.screenfakecam.infrastructure.ZxingCodeReader

/** Composition root: the only place that knows the infrastructure classes (ui talks to application ports). */
class ScreenFakeCamApp : Application() {
    val pictureLoader: PictureLoader<ImageBitmap> by lazy { AndroidPictureLoader(contentResolver) }
    val photoStore: PhotoStore<ImageBitmap> by lazy { AndroidPhotoStore(contentResolver) }
    val suggestedPhotoName: () -> String = { AndroidPhotoStore(contentResolver).fileName() }
    val codeReader: CodeReader by lazy { ZxingCodeReader() }
}
