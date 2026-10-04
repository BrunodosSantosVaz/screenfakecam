package aceite.esqueletoandante

import java.util.ServiceLoader

/**
 * What the acceptance tests of the walking skeleton need from the viewfinder (four-layer acceptance tests: the
 * scenarios talk to this driver, never to production classes, so they compile before the implementation exists).
 * The implementing task provides a [ViewfinderFactory] through
 * `app/src/test/resources/META-INF/services/aceite.esqueletoandante.ViewfinderFactory`.
 */
interface Viewfinder {
    val zoom: Float
    val offsetX: Float
    val offsetY: Float

    fun requestZoom(zoom: Float)

    fun drag(
        dx: Float,
        dy: Float,
    )
}

interface ViewfinderFactory {
    /** A viewfinder of `viewWidth`×`viewHeight` px showing an image of `imageWidth`×`imageHeight` px at 1×. */
    fun open(
        imageWidth: Int,
        imageHeight: Int,
        viewWidth: Int,
        viewHeight: Int,
    ): Viewfinder
}

fun openViewfinder(
    imageWidth: Int,
    imageHeight: Int,
    viewWidth: Int,
    viewHeight: Int,
): Viewfinder =
    ServiceLoader.load(ViewfinderFactory::class.java).firstOrNull()?.open(imageWidth, imageHeight, viewWidth, viewHeight)
        ?: throw AssertionError("nenhum ViewfinderFactory registrado: o visor ainda não foi implementado")
