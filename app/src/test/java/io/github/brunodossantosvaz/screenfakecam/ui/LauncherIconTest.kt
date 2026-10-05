package io.github.brunodossantosvaz.screenfakecam.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith

// Bug #64: the app showed Android's default icon. The global icon (docs/design/icone.svg, DOC-17) is the launcher icon.
@RunWith(AndroidJUnit4::class)
class LauncherIconTest {
    @Test
    fun theAppUsesItsOwnLauncherIcon() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val icon = context.applicationInfo.icon
        assertNotEquals("o manifesto não declara android:icon", 0, icon)
        assertEquals("mipmap", context.resources.getResourceTypeName(icon))
        assertEquals("ic_launcher", context.resources.getResourceEntryName(icon))
    }
}
