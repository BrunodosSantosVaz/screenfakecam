package io.github.brunodossantosvaz.screenfakecam.aceite

import aceite.Viewfinder
import aceite.ViewfinderFactory
import io.github.brunodossantosvaz.screenfakecam.domain.Framing

/** Acceptance test driver of the walking skeleton (tests/aceite/17-esqueleto-andante): the viewfinder is [Framing]. */
class FramingViewfinderFactory : ViewfinderFactory {
    override fun open(
        imageWidth: Int,
        imageHeight: Int,
        viewWidth: Int,
        viewHeight: Int,
    ): Viewfinder = FramingViewfinder(Framing(imageWidth, imageHeight, viewWidth, viewHeight))
}

private class FramingViewfinder(
    private var framing: Framing,
) : Viewfinder {
    override val zoom get() = framing.zoom
    override val offsetX get() = framing.offsetX
    override val offsetY get() = framing.offsetY

    override fun requestZoom(zoom: Float) {
        framing = framing.withZoom(zoom)
    }

    override fun drag(
        dx: Float,
        dy: Float,
    ) {
        framing = framing.draggedBy(dx, dy)
    }
}
