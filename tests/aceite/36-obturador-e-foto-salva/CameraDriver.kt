package aceite

import java.util.ServiceLoader

/** A photo taken by the shutter: the part of the chosen image it holds, in image pixels. */
data class PhotoTaken(
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int,
)

/**
 * What the acceptance tests of the shutter epic (#36) need: the viewfinder plus the shutter. The implementing task
 * registers a [CameraFactory] in `app/src/test/resources/META-INF/services/aceite.CameraFactory`.
 */
interface Camera {
    fun requestZoom(zoom: Float)

    fun drag(
        dx: Float,
        dy: Float,
    )

    fun pressShutter()

    /** Every photo saved so far, oldest first. */
    val photos: List<PhotoTaken>

    /** True while the chosen image was never modified (the photo is always a new file). */
    val originalUnchanged: Boolean
}

interface CameraFactory {
    fun open(
        imageWidth: Int,
        imageHeight: Int,
        viewWidth: Int,
        viewHeight: Int,
    ): Camera
}

fun openCamera(
    imageWidth: Int,
    imageHeight: Int,
    viewWidth: Int,
    viewHeight: Int,
): Camera =
    ServiceLoader
        .load(CameraFactory::class.java)
        .firstOrNull()
        ?.open(imageWidth, imageHeight, viewWidth, viewHeight)
        ?: throw AssertionError("nenhum CameraFactory registrado: o obturador ainda não foi implementado")
