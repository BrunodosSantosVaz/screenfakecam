package io.github.brunodossantosvaz.screenfakecam.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LinkPolicyTest {
    @Test
    fun webAddressesOpen() {
        assertTrue(LinkPolicy.canOpen("https://exemplo.com"))
        assertTrue(LinkPolicy.canOpen("HTTP://EXEMPLO.COM/a?b=1#c"))
        assertTrue(LinkPolicy.canOpen("  https://exemplo.com  "))
    }

    @Test
    fun everythingElseNeverOpens() {
        val never =
            listOf(
                "javascript:alert(1)",
                "intent://scan#Intent;scheme=zxing;end",
                "file:///sdcard/a.txt",
                "content://media/x",
                "tel:+5511999999999",
                "https://exemplo.com com espaço",
                "https://",
                "texto qualquer",
                "",
            )
        for (text in never) assertFalse(text, LinkPolicy.canOpen(text))
    }
}
