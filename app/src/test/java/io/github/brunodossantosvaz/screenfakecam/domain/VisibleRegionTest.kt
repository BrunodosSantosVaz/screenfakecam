package io.github.brunodossantosvaz.screenfakecam.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class VisibleRegionTest {
    private val wide = Framing(imageWidth = 1000, imageHeight = 800, viewWidth = 500, viewHeight = 400)

    @Test
    fun oneTimesIsTheWholeImage() {
        assertEquals(ImageRegion(0, 0, 1000, 800), wide.visibleRegion())
    }

    @Test
    fun zoomShowsTheCenter() {
        assertEquals(ImageRegion(250, 200, 500, 400), wide.withZoom(2f).visibleRegion())
        assertEquals(ImageRegion(375, 300, 250, 200), wide.withZoom(4f).visibleRegion())
    }

    @Test
    fun dragMovesTheRegionUpToTheBorder() {
        assertEquals(ImageRegion(0, 200, 500, 400), wide.withZoom(2f).draggedBy(10_000f, 0f).visibleRegion())
        assertEquals(ImageRegion(500, 400, 500, 400), wide.withZoom(2f).draggedBy(-10_000f, -10_000f).visibleRegion())
    }

    @Test
    fun theLetterboxBandsAreNeverPartOfThePhoto() {
        // 400x1600 in a 500x400 view: fit 0.25, shown 100x400 with empty bands left and right.
        val tall = Framing(400, 1600, 500, 400)
        assertEquals(ImageRegion(0, 0, 400, 1600), tall.visibleRegion())
        // at 2x the image is 200x800 wide: still narrower than the view, only the vertical part is cut
        assertEquals(ImageRegion(0, 400, 400, 800), tall.withZoom(2f).visibleRegion())
    }
}
