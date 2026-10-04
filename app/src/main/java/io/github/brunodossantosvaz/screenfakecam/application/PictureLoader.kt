package io.github.brunodossantosvaz.screenfakecam.application

/** A picture ready to show: the platform image ([T]) and its size in pixels after orientation. */
data class Picture<T>(
    val image: T,
    val width: Int,
    val height: Int,
)

/**
 * Loads the picture the user chose in the system photo picker. The app never keeps a copy (PRODUTO.md): the
 * picture lives only in memory while it is on screen.
 */
fun interface PictureLoader<T> {
    /** `source` is the picker's content URI; `maxSide` bounds the decoded size. Null when it cannot be read. */
    suspend fun load(
        source: String,
        maxSide: Int,
    ): Picture<T>?
}
