package io.github.brunodossantosvaz.screenfakecam.application

import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import io.github.brunodossantosvaz.screenfakecam.domain.ImageRegion

/** Where a photo was saved, as a content URI the user can open or share, and its size. */
data class SavedPhoto(
    val location: String,
    val width: Int,
    val height: Int,
)

/**
 * Saves a NEW image with the [region] of [picture]; never writes to the chosen image (RN-0005). [destination] is a
 * place the user chose (Android 8/9 "save as"); null means the gallery. Null result on failure.
 */
fun interface PhotoStore<T> {
    suspend fun save(
        picture: Picture<T>,
        region: ImageRegion,
        destination: String?,
    ): SavedPhoto?
}

/** The shutter: a photo exists only when it is pressed (RN-0005), and it is what the viewfinder shows (RN-0004). */
class Shutter<T>(
    private val store: PhotoStore<T>,
) {
    suspend fun press(
        picture: Picture<T>,
        framing: Framing,
        destination: String? = null,
    ): SavedPhoto? = store.save(picture, framing.visibleRegion(), destination)
}
