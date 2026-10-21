package documentacao

import java.io.File
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReadmeTest {
    private fun raiz(): File = generateSequence(File("").absoluteFile) { it.parentFile }.first { File(it, "README.md").exists() }

    @Test
    fun readmeDocumentaOModeloSemInternet() {
        val manifesto = File(raiz(), "app/src/main/AndroidManifest.xml").readText()
        assertFalse("o manifesto não pode declarar INTERNET", manifesto.contains("android.permission.INTERNET"))
        assertTrue("o README deve documentar que a permissão INTERNET não é declarada", File(raiz(), "README.md").readText().contains("INTERNET"))
    }
}
