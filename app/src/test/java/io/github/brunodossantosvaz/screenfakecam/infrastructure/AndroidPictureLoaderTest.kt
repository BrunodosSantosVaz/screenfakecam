package io.github.brunodossantosvaz.screenfakecam.infrastructure

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/** Loads real image files through the ContentResolver (homologation of v0.1.0-rc.1 found every image failing). */
@RunWith(AndroidJUnit4::class)
class AndroidPictureLoaderTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val loader = AndroidPictureLoader(context.contentResolver)

    private fun image(
        name: String,
        width: Int,
        height: Int,
        format: Bitmap.CompressFormat,
    ): File {
        val file = File(context.cacheDir, name)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        file.outputStream().use { bitmap.compress(format, 90, it) }
        return file
    }

    @Test
    fun loadsAPng() {
        val file = image("teste.png", 160, 120, Bitmap.CompressFormat.PNG)
        val picture = runBlocking { loader.load(Uri.fromFile(file).toString(), maxSide = 4000) }
        assertNotNull(picture)
        assertEquals(160, picture!!.width)
        assertEquals(120, picture.height)
    }

    @Test
    fun downsamplesALargeImage() {
        val file = image("grande.png", 1600, 1200, Bitmap.CompressFormat.PNG)
        val picture = runBlocking { loader.load(Uri.fromFile(file).toString(), maxSide = 500) }
        // the largest power-of-two reduction that keeps the long side >= maxSide: 1600 / 2 = 800
        assertEquals(800, picture!!.width)
        assertEquals(600, picture.height)
    }

    @Test
    fun turnsAJpegUprightByItsExifOrientation() {
        val file = image("girada.jpg", 160, 120, Bitmap.CompressFormat.JPEG)
        ExifInterface(file.absolutePath).apply {
            setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90.toString())
            saveAttributes()
        }
        val picture = runBlocking { loader.load(Uri.fromFile(file).toString(), maxSide = 4000) }
        assertEquals(120, picture!!.width)
        assertEquals(160, picture.height)
    }

    @Test
    fun aMissingFileIsNull() {
        val picture =
            runBlocking { loader.load(Uri.fromFile(File(context.cacheDir, "nao-existe.png")).toString(), 4000) }
        assertNull(picture)
    }
}
