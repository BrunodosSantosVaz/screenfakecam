package aceite

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Obturador e foto salva (#36). Imagem de 1000×800 num visor de 500×400: a 1× a foto é a imagem inteira; a 2× o
 * visor mostra 500×400 da imagem, que pode andar até a borda.
 */
class ObturadorTest {
    @get:Rule
    val pendentes = PendingRule()

    private fun camera() = openCamera(imageWidth = 1000, imageHeight = 800, viewWidth = 500, viewHeight = 400)

    @Test
    @Pendente // pendente da tarefa #39
    fun `RN-0004 CA-1 a 1x a foto e a imagem inteira`() {
        val camera = camera()
        camera.pressShutter()
        assertEquals(listOf(PhotoTaken(0, 0, 1000, 800)), camera.photos)
    }

    @Test
    @Pendente // pendente da tarefa #39
    fun `RN-0004 CA-2 a 2x centralizado a foto e o centro da imagem`() {
        val camera = camera()
        camera.requestZoom(2f)
        camera.pressShutter()
        assertEquals(listOf(PhotoTaken(250, 200, 500, 400)), camera.photos)
    }

    @Test
    @Pendente // pendente da tarefa #39
    fun `RN-0004 CA-3 a 2x na borda esquerda a foto comeca na borda esquerda`() {
        val camera = camera()
        camera.requestZoom(2f)
        camera.drag(10_000f, 0f)
        camera.pressShutter()
        assertEquals(listOf(PhotoTaken(0, 200, 500, 400)), camera.photos)
    }

    @Test
    @Pendente // pendente da tarefa #40
    fun `RN-0005 CA-4 sem apertar o obturador nenhuma foto e gerada`() {
        val camera = camera()
        camera.requestZoom(4f)
        camera.drag(30f, -30f)
        assertTrue(camera.photos.isEmpty())
        camera.pressShutter()
        assertEquals(1, camera.photos.size)
    }

    @Test
    @Pendente // pendente da tarefa #40
    fun `RN-0005 CA-5 a foto e um arquivo novo e a imagem escolhida nao muda`() {
        val camera = camera()
        camera.pressShutter()
        camera.requestZoom(2f)
        camera.pressShutter()
        assertEquals(2, camera.photos.size)
        assertTrue(camera.originalUnchanged)
    }
}
