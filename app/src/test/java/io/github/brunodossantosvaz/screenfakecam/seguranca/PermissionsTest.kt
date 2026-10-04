package io.github.brunodossantosvaz.screenfakecam.seguranca

import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

/** PRODUTO.md (ASVS L1 conditions): no internet, no broad storage, no camera. Checks the merged manifest. */
@RunWith(AndroidJUnit4::class)
class PermissionsTest {
    private val forbidden =
        listOf(
            "android.permission.INTERNET",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_MEDIA_IMAGES",
            "android.permission.MANAGE_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
        )

    @Test
    fun appRequestsNoForbiddenPermission() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
        val requested = info.requestedPermissions?.toList().orEmpty()
        for (permission in forbidden) {
            assertFalse("$permission must not be requested", permission in requested)
        }
    }
}
