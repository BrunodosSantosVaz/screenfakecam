package io.github.brunodossantosvaz.screenfakecam.infrastructure

import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.ReaderException
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import io.github.brunodossantosvaz.screenfakecam.application.CodeReader
import io.github.brunodossantosvaz.screenfakecam.application.DecodedCode

/** Offline reader of QR codes and barcodes with ZXing (pure Java: no Android, no network). */
class ZxingCodeReader : CodeReader {
    private val hints =
        mapOf(
            DecodeHintType.TRY_HARDER to true,
            DecodeHintType.POSSIBLE_FORMATS to FORMATS,
        )

    override fun read(
        pixels: IntArray,
        width: Int,
        height: Int,
    ): DecodedCode? {
        if (width <= 0 || height <= 0 || pixels.size < width * height) return null
        val source = RGBLuminanceSource(width, height, pixels)
        // HybridBinarizer handles photos and uneven light; GlobalHistogram is better for small, flat codes.
        for (bitmap in listOf(BinaryBitmap(HybridBinarizer(source)), BinaryBitmap(GlobalHistogramBinarizer(source)))) {
            val reader = MultiFormatReader().apply { setHints(hints) }
            try {
                val result = reader.decodeWithState(bitmap)
                return DecodedCode(result.text, result.barcodeFormat.name)
            } catch (_: NotFoundException) {
                continue
            } catch (_: ReaderException) {
                continue
            }
        }
        return null
    }

    companion object {
        val FORMATS =
            listOf(
                BarcodeFormat.QR_CODE,
                BarcodeFormat.DATA_MATRIX,
                BarcodeFormat.AZTEC,
                BarcodeFormat.PDF_417,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR,
            )
    }
}
