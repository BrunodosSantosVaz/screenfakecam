package aceite

import java.util.ServiceLoader

/** A code read from an image: its text and its type (ZXing format name, such as QR_CODE or EAN_13). */
data class CodeRead(
    val text: String,
    val format: String,
)

/**
 * What the acceptance tests of the code reader epic (#37) need. Images are ARGB pixels, row by row. The implementing
 * task registers a [CodeReaderDriver] in `app/src/test/resources/META-INF/services/aceite.CodeReaderDriver`.
 */
interface CodeReaderDriver {
    fun read(
        pixels: IntArray,
        width: Int,
        height: Int,
    ): CodeRead?

    fun canOpenAsLink(text: String): Boolean

    /** A test image (ARGB pixels) holding `text` encoded as `format`, with a white margin. */
    fun sampleImage(
        text: String,
        format: String,
        width: Int,
        height: Int,
    ): TestImage
}

class TestImage(
    val pixels: IntArray,
    val width: Int,
    val height: Int,
)

fun codeReader(): CodeReaderDriver =
    ServiceLoader.load(CodeReaderDriver::class.java).firstOrNull()
        ?: throw AssertionError("nenhum CodeReaderDriver registrado: o leitor de códigos ainda não foi implementado")
