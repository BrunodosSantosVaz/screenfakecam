package io.github.brunodossantosvaz.screenfakecam.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class FramingTest {
    // 1000x800 image in a 500x400 view: fit scale 0.5, so 2x shows it at 1000x800 (limits 250 and 200).
    private val framing = Framing(imageWidth = 1000, imageHeight = 800, viewWidth = 500, viewHeight = 400)

    @Test
    fun startsAtOneTimesCentered() {
        assertEquals(1f, framing.zoom)
        assertEquals(0.5f, framing.fitScale)
        assertEquals(0f, framing.offsetX)
        assertEquals(0f, framing.offsetY)
    }

    @Test
    fun zoomStaysBetweenOneAndFour() {
        assertEquals(2.5f, framing.withZoom(2.5f).zoom)
        assertEquals(4f, framing.withZoom(10f).zoom)
        assertEquals(1f, framing.withZoom(0f).zoom)
    }

    @Test
    fun dragStopsAtTheBorder() {
        val zoomed = framing.withZoom(2f).draggedBy(1000f, -1000f)
        assertEquals(250f, zoomed.offsetX)
        assertEquals(-200f, zoomed.offsetY)
    }

    @Test
    fun zoomingOutPullsTheImageBackInside() {
        val moved = framing.withZoom(4f).draggedBy(5000f, 5000f)
        val back = moved.withZoom(1f)
        assertEquals(0f, back.offsetX)
        assertEquals(0f, back.offsetY)
    }

    @Test
    fun aTallImageOnlyMovesWhereItOverflows() {
        // 400x1600 in 500x400: fit scale 0.25, at 2x it is 200x800: only vertical movement.
        val tall = Framing(400, 1600, 500, 400).withZoom(2f).draggedBy(300f, 300f)
        assertEquals(0f, tall.offsetX)
        assertEquals(200f, tall.offsetY)
    }

    @Test
    fun refusesEmptySizes() {
        assertThrows(IllegalArgumentException::class.java) { Framing(0, 10, 10, 10) }
        assertThrows(IllegalArgumentException::class.java) { Framing(10, 10, 10, 0) }
    }
}
