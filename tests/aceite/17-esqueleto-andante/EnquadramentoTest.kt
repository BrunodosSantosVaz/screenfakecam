package aceite.esqueletoandante

import aceite.suporte.PendingRule
import aceite.suporte.Pendente
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Esqueleto andante (#17): zoom e enquadramento do visor. Imagem de 1000×800 num visor de 500×400: a 1× a imagem
 * inteira cabe no visor; a 2× ela ocupa 1000×800 e pode andar até 250 na horizontal e 200 na vertical.
 */
class EnquadramentoTest {
    @get:Rule
    val pendentes = PendingRule()

    private fun visor() = openViewfinder(imageWidth = 1000, imageHeight = 800, viewWidth = 500, viewHeight = 400)

    @Test
    @Pendente // pendente da tarefa #20
    fun `RN-0001 CA-1 pedir 2x a partir de 1x deixa o zoom em 2x`() {
        val visor = visor()
        visor.requestZoom(2f)
        assertEquals(2f, visor.zoom, 0.001f)
    }

    @Test
    @Pendente // pendente da tarefa #20
    fun `RN-0001 CA-2 a 4x pedir mais zoom deixa o zoom em 4x`() {
        val visor = visor()
        visor.requestZoom(4f)
        visor.requestZoom(8f)
        assertEquals(4f, visor.zoom, 0.001f)
    }

    @Test
    @Pendente // pendente da tarefa #20
    fun `RN-0001 CA-3 a 1x pedir menos zoom deixa o zoom em 1x`() {
        val visor = visor()
        visor.requestZoom(0.5f)
        assertEquals(1f, visor.zoom, 0.001f)
    }

    @Test
    @Pendente // pendente da tarefa #20
    fun `RN-0002 CA-4 a 2x arrastar alem da borda para na borda`() {
        val visor = visor()
        visor.requestZoom(2f)
        visor.drag(1000f, -1000f)
        assertEquals(250f, visor.offsetX, 0.001f)
        assertEquals(-200f, visor.offsetY, 0.001f)
    }

    @Test
    @Pendente // pendente da tarefa #20
    fun `RN-0002 CA-5 a 1x arrastar nao move a imagem`() {
        val visor = visor()
        visor.drag(100f, 100f)
        assertEquals(0f, visor.offsetX, 0.001f)
        assertEquals(0f, visor.offsetY, 0.001f)
    }
}
