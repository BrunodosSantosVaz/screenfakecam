package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.brunodossantosvaz.screenfakecam.application.PhotoStore
import io.github.brunodossantosvaz.screenfakecam.application.Picture
import io.github.brunodossantosvaz.screenfakecam.application.PictureLoader
import io.github.brunodossantosvaz.screenfakecam.application.SavedPhoto
import io.github.brunodossantosvaz.screenfakecam.application.Shutter
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import kotlinx.coroutines.launch

/** State of the viewfinder: the chosen picture (only in memory) and its [Framing]. */
class ViewfinderViewModel(
    private val loader: PictureLoader<ImageBitmap>,
    store: PhotoStore<ImageBitmap>,
) : ViewModel() {
    private val shutter = Shutter(store)

    /** The last photo taken (RN-0005: only after the shutter), and its picture for the "Foto salva" screen. */
    var lastPhoto by mutableStateOf<SavedPhoto?>(null)
        private set
    var lastPhotoPicture by mutableStateOf<Picture<ImageBitmap>?>(null)
        private set
    var showingSaved by mutableStateOf(false)
        private set
    var saving by mutableStateOf(false)
        private set
    var saveFailed by mutableStateOf(false)
        private set

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

    /** RN-0004/RN-0005: saves what the viewfinder shows; `destination` is the "save as" choice on Android 8/9. */
    fun takePhoto(destination: String?) {
        val current = picture ?: return
        val currentFraming = framing ?: return
        saving = true
        saveFailed = false
        viewModelScope.launch {
            val saved = shutter.press(current, currentFraming, destination)
            saving = false
            if (saved == null) {
                saveFailed = true
            } else {
                lastPhoto = saved
                lastPhotoPicture = loader.load(saved.location, maxOf(saved.width, saved.height))
                showingSaved = true
            }
        }
    }

    fun showSaved() {
        if (lastPhoto != null) showingSaved = true
    }

    fun backToViewfinder() {
        showingSaved = false
    }

    fun close() {
        picture = null
        framing = null
    }
}
