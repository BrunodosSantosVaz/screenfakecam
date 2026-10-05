package io.github.brunodossantosvaz.screenfakecam.application

/** A code read from an image (RN-0006): its full text and its type, such as QR_CODE or EAN_13. */
data class DecodedCode(
    val text: String,
    val format: String,
)

/** Reads a QR code or barcode from ARGB pixels (row by row). Null when the image has no readable code. */
fun interface CodeReader {
    fun read(
        pixels: IntArray,
        width: Int,
        height: Int,
    ): DecodedCode?
}
