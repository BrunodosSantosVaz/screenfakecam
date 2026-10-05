package arquitetura

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.Test

/** Layer rules of STACK.md, checked on the production sources (`bigbang.toml` comandos.arquitetura). */
class LayersTest {
    private val base = "io.github.brunodossantosvaz.screenfakecam"
    private val production = Konsist.scopeFromProduction()

    @Test
    fun domainIsPureKotlin() {
        production.files
            .filter { it.packagee?.name?.startsWith("$base.domain") == true }
            .assertFalse { file ->
                file.imports.any {
                    it.name.startsWith("android") ||
                        it.name.startsWith("$base.") &&
                        !it.name.startsWith("$base.domain")
                }
            }
    }

    @Test
    fun applicationDependsOnlyOnDomain() {
        production.files
            .filter { it.packagee?.name?.startsWith("$base.application") == true }
            .assertFalse { file ->
                file.imports.any {
                    it.name.startsWith("$base.ui") ||
                        it.name.startsWith("$base.infrastructure")
                }
            }
    }

    @Test
    fun uiNeverCallsInfrastructureDirectly() {
        production.files
            .filter { it.packagee?.name?.startsWith("$base.ui") == true }
            .assertFalse { file -> file.imports.any { it.name.startsWith("$base.infrastructure") } }
    }
}
