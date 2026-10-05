package io.github.brunodossantosvaz.screenfakecam.infrastructure

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.domain.ImageRegion
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.Date

@RunWith(AndroidJUnit4::class)
class AndroidPhotoStoreTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val store = AndroidPhotoStore(context.contentResolver) { Date(1_791_158_400_000) }

    private fun picture(): Pair<Picture<androidx.compose.ui.graphics.ImageBitmap>, Bitmap> {
        // left half red, right half blue: the crop of the right half must be blue
        val bitmap = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888)
        for (x in 0 until 200) for (y in 0 until 100) bitmap.setPixel(x, y, if (x < 100) Color.RED else Color.BLUE)
        return Picture(bitmap.asImageBitmap(), 200, 100) to bitmap
    }

    @Test
    fun savesTheRegionAsANewJpegWhereTheUserChose() {
        val (picture, original) = picture()
        val before = IntArray(200 * 100).also { original.getPixels(it, 0, 200, 0, 0, 200, 100) }
        val file = File(context.cacheDir, "foto.jpg")
        val saved = runBlocking { store.save(picture, ImageRegion(100, 0, 100, 100), Uri.fromFile(file).toString()) }
        assertNotNull(saved)
        assertEquals(100, saved!!.width)
        val photo = BitmapFactory.decodeFile(file.absolutePath)
        assertEquals(100, photo.width)
        assertEquals(100, photo.height)
        assertTrue(Color.blue(photo.getPixel(50, 50)) > 200)
        val after = IntArray(200 * 100).also { original.getPixels(it, 0, 200, 0, 0, 200, 100) }
        assertTrue(before.contentEquals(after)) // RN-0005: the chosen image is never written
    }

    @Test
    fun aRegionOutsideTheImageIsClampedInside() {
        val (picture, _) = picture()
        val file = File(context.cacheDir, "borda.jpg")
        val saved = runBlocking { store.save(picture, ImageRegion(150, 50, 500, 500), Uri.fromFile(file).toString()) }
        assertEquals(50, saved!!.width)
        assertEquals(50, saved.height)
    }

    @Test
    fun theFileNameHasTheDateAndTime() {
        assertTrue(store.fileName().matches(Regex("ScreenFakeCam_\\d{8}_\\d{6}\\.jpg")))
    }
}
