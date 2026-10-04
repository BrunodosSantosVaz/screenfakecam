package aceite

import android.content.Context
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Esqueleto andante (#17): o app instalado não pede acesso que o produto proíbe (PRODUTO.md, ASVS L1). */
@RunWith(AndroidJUnit4::class)
class PermissoesTest {
    @get:Rule
    val pendentes = PendingRule()

    @Test
    @Pendente // pendente da tarefa #19
    fun `RN-0003 CA-6 o manifesto nao pede internet armazenamento amplo nem camera`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
        val pedidas = info.requestedPermissions?.toList().orEmpty()
        for (proibida in listOf(
            "android.permission.INTERNET",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_MEDIA_IMAGES",
            "android.permission.MANAGE_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
        )) {
            assertFalse("$proibida não pode ser pedida", proibida in pedidas)
        }
    }
}
