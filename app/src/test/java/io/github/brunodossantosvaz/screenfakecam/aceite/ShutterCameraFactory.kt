package io.github.brunodossantosvaz.screenfakecam.aceite

import aceite.Camera
import aceite.CameraFactory
import aceite.PhotoTaken
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.application.SavedPhoto
import io.github.brunodossantosvaz.screenfakecam.application.Shutter
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import kotlinx.coroutines.runBlocking

/** Acceptance driver of the shutter epic (#36): [Framing] + [Shutter] with an in-memory store of pixel images. */
class ShutterCameraFactory : CameraFactory {
    override fun open(
        imageWidth: Int,
        imageHeight: Int,
        viewWidth: Int,
        viewHeight: Int,
    ): Camera = ShutterCamera(imageWidth, imageHeight, viewWidth, viewHeight)
}

private class ShutterCamera(
    imageWidth: Int,
    imageHeight: Int,
    viewWidth: Int,
    viewHeight: Int,
) : Camera {
    private val original = IntArray(imageWidth * imageHeight) { it }
    private val snapshot = original.copyOf()
    private val picture = Picture(original, imageWidth, imageHeight)
    private var framing = Framing(imageWidth, imageHeight, viewWidth, viewHeight)
    private val saved = mutableListOf<Pair<PhotoTaken, IntArray>>()

    private val shutter =
        Shutter<IntArray> { chosen, region ->
            val copy = IntArray(region.width * region.height)
            for (y in 0 until region.height) {
                System.arraycopy(
                    chosen.image,
                    (region.top + y) * chosen.width + region.left,
                    copy,
                    y * region.width,
                    region.width,
                )
            }
            saved += PhotoTaken(region.left, region.top, region.width, region.height) to copy
            SavedPhoto("memoria://${saved.size}", region.width, region.height)
        }

    override fun requestZoom(zoom: Float) {
        framing = framing.withZoom(zoom)
    }

    override fun drag(
        dx: Float,
        dy: Float,
    ) {
        framing = framing.draggedBy(dx, dy)
    }

    override fun pressShutter() {
        runBlocking { shutter.press(picture, framing) }
    }

    override val photos: List<PhotoTaken> get() = saved.map { it.first }

    override val originalUnchanged: Boolean get() =
        original.contentEquals(snapshot) &&
            saved.none { it.second === original }
}
