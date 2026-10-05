package io.github.brunodossantosvaz.screenfakecam.ui

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.brunodossantosvaz.screenfakecam.application.CodeReader
import io.github.brunodossantosvaz.screenfakecam.application.DecodedCode
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.domain.ImageRegion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** The outcome of reading a code (RN-0006): found, or no code in the image. */
sealed interface CodeOutcome {
    data class Found(
        val code: DecodedCode,
    ) : CodeOutcome

    data object NotFound : CodeOutcome
}

/** Reads QR codes and barcodes from the chosen picture (whole, from the start screen) or the viewfinder framing. */
class CodeViewModel(
    private val reader: CodeReader,
) : ViewModel() {
    var outcome by mutableStateOf<CodeOutcome?>(null)
        private set
    var reading by mutableStateOf(false)
        private set
    var fromViewfinder by mutableStateOf(false)
        private set

    /** The start screen asked to read: the next picture loaded is read whole. */
    var waitingForPicture by mutableStateOf(false)
        private set

    fun readNextPicture() {
        waitingForPicture = true
        outcome = null
    }

    fun read(
        picture: Picture<ImageBitmap>,
        region: ImageRegion?,
        fromViewfinder: Boolean,
    ) {
        waitingForPicture = false
        this.fromViewfinder = fromViewfinder
        reading = true
        viewModelScope.launch {
            val code =
                withContext(Dispatchers.Default) {
                    val (pixels, width, height) =
                        pixelsOf(
                            picture.image,
                            region ?: ImageRegion(0, 0, picture.width, picture.height),
                        )
                    reader.read(pixels, width, height)
                }
            outcome = code?.let { CodeOutcome.Found(it) } ?: CodeOutcome.NotFound
            reading = false
        }
    }

    fun clear() {
        outcome = null
        waitingForPicture = false
    }

    private companion object {
        const val MAX_SIDE = 1600 // enough for ZXing; avoids huge pixel arrays

        fun pixelsOf(
            image: ImageBitmap,
            region: ImageRegion,
        ): Triple<IntArray, Int, Int> {
            val source = image.asAndroidBitmap()
            val left = region.left.coerceIn(0, source.width - 1)
            val top = region.top.coerceIn(0, source.height - 1)
            var bitmap =
                Bitmap.createBitmap(
                    source,
                    left,
                    top,
                    region.width.coerceIn(1, source.width - left),
                    region.height.coerceIn(
                        1,
                        source.height - top,
                    ),
                )
            val longest = maxOf(bitmap.width, bitmap.height)
            if (longest > MAX_SIDE) {
                bitmap =
                    Bitmap.createScaledBitmap(
                        bitmap,
                        bitmap.width * MAX_SIDE / longest,
                        bitmap.height * MAX_SIDE / longest,
                        true,
                    )
            }
            val pixels = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            return Triple(pixels, bitmap.width, bitmap.height)
        }
    }
}
