package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.application.PictureLoader
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import kotlinx.coroutines.launch

/** State of the viewfinder: the chosen picture (only in memory) and its [Framing]. */
class ViewfinderViewModel(
    private val loader: PictureLoader<ImageBitmap>,
) : ViewModel() {
    var picture by mutableStateOf<Picture<ImageBitmap>?>(null)
        private set
    var framing by mutableStateOf<Framing?>(null)
        private set
    var loading by mutableStateOf(false)
        private set
    var failed by mutableStateOf(false)
        private set

    fun onPicked(
        source: String,
        maxSide: Int,
    ) {
        loading = true
        failed = false
        viewModelScope.launch {
            val loaded = loader.load(source, maxSide)
            picture = loaded
            failed = loaded == null
            framing = null
            loading = false
        }
    }

    fun onViewSize(
        width: Int,
        height: Int,
    ) {
        val current = picture ?: return
        if (width <= 0 || height <= 0) return
        val old = framing
        if (old == null || old.viewWidth != width || old.viewHeight != height) {
            framing = Framing(current.width, current.height, width, height, zoom = old?.zoom ?: Framing.MIN_ZOOM)
        }
    }

    fun zoomTo(zoom: Float) {
        framing = framing?.withZoom(zoom)
    }

    fun zoomBy(factor: Float) {
        framing = framing?.let { it.withZoom(it.zoom * factor) }
    }

    fun drag(
        dx: Float,
        dy: Float,
    ) {
        framing = framing?.draggedBy(dx, dy)
    }

    fun close() {
        picture = null
        framing = null
    }
}
