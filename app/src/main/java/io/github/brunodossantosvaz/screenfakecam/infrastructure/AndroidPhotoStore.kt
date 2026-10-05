package io.github.brunodossantosvaz.screenfakecam.infrastructure

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import io.github.brunodossantosvaz.screenfakecam.application.PhotoStore
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.application.SavedPhoto
import io.github.brunodossantosvaz.screenfakecam.domain.ImageRegion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "ScreenFakeCam"
private const val JPEG_QUALITY = 92

/**
 * Saves the photo as a new JPEG (RN-0005) without any storage permission (RN-0003): in the gallery
 * (Pictures/ScreenFakeCam) on Android 10+, or in the place the user chose with the system "save as" on Android 8/9.
 */
class AndroidPhotoStore(
    private val resolver: ContentResolver,
    private val now: () -> Date = ::Date,
) : PhotoStore<ImageBitmap> {
    override suspend fun save(
        picture: Picture<ImageBitmap>,
        region: ImageRegion,
        destination: String?,
    ): SavedPhoto? =
        withContext(Dispatchers.IO) {
            runCatching {
                val photo = crop(picture.image.asAndroidBitmap(), region)
                val uri =
                    when {
                        destination != null -> Uri.parse(destination).also { write(it, photo) }
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> insertInGallery(photo)
                        else -> null // Android 8/9: the screen asks for a destination first
                    }
                uri?.let { SavedPhoto(it.toString(), photo.width, photo.height) }
            }.onFailure { Log.w(TAG, "could not save the photo: ${it.javaClass.simpleName}") }
                .getOrNull()
        }

    fun fileName(): String = "ScreenFakeCam_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(now())}.jpg"

    private fun crop(
        source: Bitmap,
        region: ImageRegion,
    ): Bitmap {
        val left = region.left.coerceIn(0, source.width - 1)
        val top = region.top.coerceIn(0, source.height - 1)
        val width = region.width.coerceIn(1, source.width - left)
        val height = region.height.coerceIn(1, source.height - top)
        // Bitmap.createBitmap returns a NEW bitmap for a sub-region; the chosen image is never written.
        return Bitmap.createBitmap(source, left, top, width, height)
    }

    private fun write(
        uri: Uri,
        photo: Bitmap,
    ) {
        val stream = resolver.openOutputStream(uri) ?: error("no output stream")
        stream.use { check(photo.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, it)) { "compress failed" } }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun insertInGallery(photo: Bitmap): Uri? {
        val values =
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName())
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ScreenFakeCam")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val uri = resolver.insert(collection, values) ?: return null
        try {
            write(uri, photo)
            resolver.update(uri, ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) }, null, null)
        } catch (failure: Exception) {
            resolver.delete(uri, null, null) // no half-written photo left in the gallery
            throw failure
        }
        return uri
    }
}
