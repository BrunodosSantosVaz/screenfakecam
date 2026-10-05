package io.github.brunodossantosvaz.screenfakecam.application

import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import io.github.brunodossantosvaz.screenfakecam.domain.ImageRegion
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ShutterTest {
    private val picture = Picture(image = "imagem", width = 1000, height = 800)

    @Test
    fun savesTheVisibleRegion() {
        val asked = mutableListOf<ImageRegion>()
        val shutter =
            Shutter<String> { _, region, _ ->
                asked += region
                SavedPhoto("content://foto/1", region.width, region.height)
            }
        val saved = runBlocking { shutter.press(picture, Framing(1000, 800, 500, 400).withZoom(2f)) }
        assertEquals(listOf(ImageRegion(250, 200, 500, 400)), asked)
        assertEquals(SavedPhoto("content://foto/1", 500, 400), saved)
    }

    @Test
    fun aFailedSaveIsNull() {
        val shutter = Shutter<String> { _, _, _ -> null }
        assertNull(runBlocking { shutter.press(picture, Framing(1000, 800, 500, 400)) })
    }
}
