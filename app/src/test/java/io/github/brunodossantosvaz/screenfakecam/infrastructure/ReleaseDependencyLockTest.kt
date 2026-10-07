package io.github.brunodossantosvaz.screenfakecam.infrastructure

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/** Regression for #72: the source checkout used for the SBOM needs the resolved Android release dependencies. */
class ReleaseDependencyLockTest {
    @Test
    fun releaseLockContainsRuntimeDependenciesWithResolvedVersions() {
        val lock = File("gradle.lockfile")
        assertTrue("the release dependency lock is missing from the app source checkout", lock.isFile)
        val dependencies =
            lock.readLines().filter { line ->
                !line.startsWith("#") && line.substringAfter("=", "").split(",").contains("releaseRuntimeClasspath")
            }
        assertFalse("the release runtime dependency graph is empty", dependencies.isEmpty())
        for (module in REQUIRED_RUNTIME_MODULES) {
            val entries = dependencies.filter { it.startsWith("$module:") }
            assertTrue("the release dependency lock omits $module", entries.isNotEmpty())
            for (entry in entries) {
                val version = entry.substringBefore("=").split(":").last()
                assertTrue("$module must have a resolved version", version.matches(RESOLVED_VERSION))
            }
        }
        assertFalse("Compose test tooling must not be represented as release runtime", dependencies.any {
            it.startsWith("androidx.compose.ui:ui-test")
        })
    }

    companion object {
        private val RESOLVED_VERSION = Regex("[0-9][A-Za-z0-9.\\-]*")
        private val REQUIRED_RUNTIME_MODULES =
            listOf(
                "com.google.zxing:core",
                "androidx.core:core-ktx",
                "androidx.activity:activity-compose",
                "androidx.compose.ui:ui-android",
                "androidx.compose.material3:material3-android",
                "org.jetbrains.kotlin:kotlin-stdlib",
            )
    }
}
