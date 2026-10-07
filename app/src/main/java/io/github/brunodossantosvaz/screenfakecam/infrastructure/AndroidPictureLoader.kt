package io.github.brunodossantosvaz.screenfakecam.infrastructure

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.exifinterface.media.ExifInterface
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.application.PictureLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "ScreenFakeCam"

/** Reads the chosen image through the ContentResolver (no storage permission), downsampled and upright. */
class AndroidPictureLoader(
    private val resolver: ContentResolver,
) : PictureLoader<ImageBitmap> {
    override suspend fun load(
        source: String,
        maxSide: Int,
    ): Picture<ImageBitmap>? =
        withContext(Dispatchers.IO) {
            runCatching { decode(Uri.parse(source), maxSide) }
                .onFailure { Log.w(TAG, "could not decode the chosen image: ${it.javaClass.simpleName}") }
                .getOrNull()
        }

    private fun decode(
        uri: Uri,
        maxSide: Int,
    ): Picture<ImageBitmap>? {
        // With inJustDecodeBounds, decodeStream always returns null and only fills outWidth/outHeight.
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        val stream = resolver.openInputStream(uri) ?: return null
        stream.use { BitmapFactory.decodeStream(it, null, bounds) }
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null
        var sample = 1
        // Ceiling division keeps the decoded long side at most maxSide, including odd source dimensions.
        while ((maxOf(bounds.outWidth, bounds.outHeight) - 1) / sample >= maxSide) sample *= 2
        val options = BitmapFactory.Options().apply { inSampleSize = sample }
        val bitmap = resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) } ?: return null
        val upright = rotate(bitmap, orientation(uri))
        return Picture(upright.asImageBitmap(), upright.width, upright.height)
    }

    private fun orientation(uri: Uri): Int =
        resolver.openInputStream(uri)?.use {
            ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        } ?: ExifInterface.ORIENTATION_NORMAL

    private fun rotate(
        bitmap: Bitmap,
        orientation: Int,
    ): Bitmap {
        val degrees =
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> return bitmap
            }
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
