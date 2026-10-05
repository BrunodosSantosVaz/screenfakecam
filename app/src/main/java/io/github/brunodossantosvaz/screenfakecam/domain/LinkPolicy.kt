package io.github.brunodossantosvaz.screenfakecam.domain

/**
 * RN-0007: a text read from a code may be opened as a link only when it is an http or https address. Everything
 * else (javascript:, intent://, file://, content://, plain text…) is never opened: the user can copy or share it.
 */
object LinkPolicy {
    private val webAddress = Regex("^https?://[^\\s]+$", RegexOption.IGNORE_CASE)

    fun canOpen(text: String): Boolean = webAddress.matches(text.trim())
}
