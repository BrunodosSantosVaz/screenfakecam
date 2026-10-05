package io.github.brunodossantosvaz.screenfakecam.aceite

import aceite.CodeRead
import aceite.CodeReaderDriver
import aceite.TestImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import io.github.brunodossantosvaz.screenfakecam.domain.LinkPolicy
import io.github.brunodossantosvaz.screenfakecam.infrastructure.ZxingCodeReader

/** Acceptance driver of the code reader epic (#37): [ZxingCodeReader] and [LinkPolicy]. */
class ZxingCodeReaderDriver : CodeReaderDriver {
    private val reader = ZxingCodeReader()

    override fun read(
        pixels: IntArray,
        width: Int,
        height: Int,
    ): CodeRead? = reader.read(pixels, width, height)?.let { CodeRead(it.text, it.format) }

    override fun canOpenAsLink(text: String): Boolean = LinkPolicy.canOpen(text)

    override fun sampleImage(
        text: String,
        format: String,
        width: Int,
        height: Int,
    ): TestImage {
        val matrix = MultiFormatWriter().encode(text, BarcodeFormat.valueOf(format), width, height)
        val pixels = IntArray(matrix.width * matrix.height)
        for (y in 0 until matrix.height) {
            for (x in 0 until matrix.width) {
                pixels[y * matrix.width + x] = if (matrix.get(x, y)) BLACK else WHITE
            }
        }
        return TestImage(pixels, matrix.width, matrix.height)
    }

    private companion object {
        const val BLACK = 0xFF000000.toInt()
        const val WHITE = 0xFFFFFFFF.toInt()
    }
}
