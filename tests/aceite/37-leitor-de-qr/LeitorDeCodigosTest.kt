package aceite

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/** Leitor de QR e código de barras (#37). As imagens de teste vêm do driver, com margem branca em volta. */
class LeitorDeCodigosTest {
    @get:Rule
    val pendentes = PendingRule()

    @Test
    fun `RN-0006 CA-1 le o QR code de uma imagem`() {
        val leitor = codeReader()
        val imagem = leitor.sampleImage("https://exemplo.com/cardapio", "QR_CODE", 300, 300)
        assertEquals(
            CodeRead("https://exemplo.com/cardapio", "QR_CODE"),
            leitor.read(imagem.pixels, imagem.width, imagem.height),
        )
    }

    @Test
    fun `RN-0006 CA-2 le o codigo de barras EAN-13 de uma imagem`() {
        val leitor = codeReader()
        val imagem = leitor.sampleImage("7891234567895", "EAN_13", 400, 150)
        assertEquals(CodeRead("7891234567895", "EAN_13"), leitor.read(imagem.pixels, imagem.width, imagem.height))
    }

    @Test
    fun `RN-0006 CA-3 imagem sem codigo nao tem leitura`() {
        val branca = IntArray(200 * 200) { 0xFFFFFFFF.toInt() }
        assertNull(codeReader().read(branca, 200, 200))
    }

    @Test
    fun `RN-0007 CA-4 link http ou https pode ser aberto`() {
        assertTrue(codeReader().canOpenAsLink("https://exemplo.com"))
        assertTrue(codeReader().canOpenAsLink("http://exemplo.com/a?b=1"))
    }

    @Test
    fun `RN-0007 CA-5 outros esquemas nunca sao abertos`() {
        for (texto in listOf(
            "javascript:alert(1)",
            "intent://x#Intent;end",
            "file:///sdcard/a",
            "content://x/y",
            "texto",
        )) {
            assertFalse(texto, codeReader().canOpenAsLink(texto))
        }
    }
}
