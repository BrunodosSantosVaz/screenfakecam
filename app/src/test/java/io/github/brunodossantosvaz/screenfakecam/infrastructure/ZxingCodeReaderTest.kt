package io.github.brunodossantosvaz.screenfakecam.infrastructure

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import io.github.brunodossantosvaz.screenfakecam.application.DecodedCode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ZxingCodeReaderTest {
    private val reader = ZxingCodeReader()

    private fun read(
        text: String,
        format: BarcodeFormat,
        width: Int,
        height: Int,
        scale: Int = 1,
    ): DecodedCode? {
        val matrix = MultiFormatWriter().encode(text, format, width, height)
        val w = matrix.width * scale
        val h = matrix.height * scale
        val pixels =
            IntArray(w * h) { i -> if (matrix.get((i % w) / scale, (i / w) / scale)) 0xFF000000.toInt() else -1 }
        return reader.read(pixels, w, h)
    }

    @Test
    fun readsSeveralFormats() {
        assertEquals(DecodedCode("olá, mundo", "QR_CODE"), read("olá, mundo", BarcodeFormat.QR_CODE, 200, 200))
        assertEquals(DecodedCode("12345670", "EAN_8"), read("12345670", BarcodeFormat.EAN_8, 300, 120))
        assertEquals(
            DecodedCode("SCREENFAKECAM-128", "CODE_128"),
            read("SCREENFAKECAM-128", BarcodeFormat.CODE_128, 400, 120),
        )
        assertEquals(
            DecodedCode("Data Matrix", "DATA_MATRIX"),
            read("Data Matrix", BarcodeFormat.DATA_MATRIX, 120, 120, scale = 4),
        )
    }

    @Test
    fun noCodeOrInvalidSizeIsNull() {
        assertNull(reader.read(IntArray(100 * 100) { -1 }, 100, 100))
        assertNull(reader.read(IntArray(10), 0, 0))
        assertNull(reader.read(IntArray(10), 100, 100))
    }
}
