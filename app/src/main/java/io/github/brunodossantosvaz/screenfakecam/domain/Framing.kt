package io.github.brunodossantosvaz.screenfakecam.domain

import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * What the viewfinder shows of the chosen image: zoom and position. Pure Kotlin, immutable.
 *
 * At zoom 1 the whole image fits inside the view (RN-0001); the offset is how far the image center is moved from
 * the view center, in view pixels, and it never shows anything outside the image (RN-0002).
 */
data class Framing(
    val imageWidth: Int,
    val imageHeight: Int,
    val viewWidth: Int,
    val viewHeight: Int,
    val zoom: Float = MIN_ZOOM,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
) {
    init {
        require(imageWidth > 0 && imageHeight > 0) { "image size must be positive" }
        require(viewWidth > 0 && viewHeight > 0) { "view size must be positive" }
    }

    /** Scale that makes the whole image fit inside the view at zoom 1. */
    val fitScale: Float get() = min(viewWidth.toFloat() / imageWidth, viewHeight.toFloat() / imageHeight)

    /** RN-0001: a requested zoom outside 1x..4x stays at the nearest limit. */
    fun withZoom(requested: Float): Framing = copy(zoom = requested.coerceIn(MIN_ZOOM, MAX_ZOOM)).clamped()

    /** RN-0002: dragging stops at the image border. */
    fun draggedBy(
        dx: Float,
        dy: Float,
    ): Framing = copy(offsetX = offsetX + dx, offsetY = offsetY + dy).clamped()

    /**
     * RN-0004: the part of the image the viewfinder shows, in image pixels: what the shutter turns into a photo.
     * At zoom 1 it is the whole image; the empty bands around a letterboxed image are never part of it.
     */
    fun visibleRegion(): ImageRegion {
        val scale = fitScale * zoom
        val shownLeft = (viewWidth - imageWidth * scale) / 2f + offsetX
        val shownTop = (viewHeight - imageHeight * scale) / 2f + offsetY
        val left = ((max(0f, shownLeft) - shownLeft) / scale).roundToInt().coerceIn(0, imageWidth - 1)
        val top = ((max(0f, shownTop) - shownTop) / scale).roundToInt().coerceIn(0, imageHeight - 1)
        val right = ((min(viewWidth.toFloat(), shownLeft + imageWidth * scale) - shownLeft) / scale).roundToInt()
        val bottom = ((min(viewHeight.toFloat(), shownTop + imageHeight * scale) - shownTop) / scale).roundToInt()
        return ImageRegion(
            left = left,
            top = top,
            width = (right.coerceIn(left + 1, imageWidth) - left),
            height = (bottom.coerceIn(top + 1, imageHeight) - top),
        )
    }

    private fun clamped(): Framing {
        val limitX = max(0f, (imageWidth * fitScale * zoom - viewWidth) / 2f)
        val limitY = max(0f, (imageHeight * fitScale * zoom - viewHeight) / 2f)
        return copy(offsetX = offsetX.coerceIn(-limitX, limitX), offsetY = offsetY.coerceIn(-limitY, limitY))
    }

    companion object {
        const val MIN_ZOOM = 1f
        const val MAX_ZOOM = 4f
    }
}

/** A rectangle of the image, in image pixels. */
data class ImageRegion(
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int,
)
