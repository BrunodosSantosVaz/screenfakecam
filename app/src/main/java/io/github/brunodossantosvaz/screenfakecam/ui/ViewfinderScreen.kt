package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.domain.Framing
import io.github.brunodossantosvaz.screenfakecam.ui.theme.Tokens

private val ZOOM_STEPS = listOf(1f, 2f, 4f)

/** Viewfinder: the chosen image, still, with zoom and drag (RN-0001, RN-0002). The shutter comes in a later epic. */
@Composable
fun ViewfinderScreen(
    image: ImageBitmap,
    framing: Framing?,
    onViewSize: (Int, Int) -> Unit,
    onZoomTo: (Float) -> Unit,
    onZoomBy: (Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onBack: () -> Unit,
    onShutter: () -> Unit = {},
    hasLastPhoto: Boolean = false,
    onShowLastPhoto: () -> Unit = {},
    flashes: Int = 0,
    saving: Boolean = false,
    saveFailed: Boolean = false,
    onReadCode: () -> Unit = {},
) {
    // Edge-to-edge (targetSdk 35+): keep the bars and controls clear of the status and navigation bars.
    Column(Modifier.fillMaxSize().background(Tokens.background).safeDrawingPadding()) {
        Row(
            Modifier.fillMaxWidth().background(Tokens.surface).padding(horizontal = Tokens.space2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.sizeIn(minWidth = Tokens.minTouch, minHeight = Tokens.minTouch),
            ) {
                Text(stringResource(R.string.back), color = Tokens.text)
            }
            Text(
                stringResource(R.string.viewfinder_title),
                style = MaterialTheme.typography.bodyLarge,
                color = Tokens.text,
                modifier = Modifier.weight(1f),
            )
            // RN-0006: read the code of what the viewfinder shows (zoom in on a small code first)
            TextButton(
                onClick = onReadCode,
                modifier = Modifier.sizeIn(minWidth = Tokens.minTouch, minHeight = Tokens.minTouch),
            ) {
                Text(stringResource(R.string.read_code_here), color = Tokens.primary)
            }
        }
        val description = stringResource(R.string.viewfinder_description)
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .clipToBounds()
                .onSizeChanged { onViewSize(it.width, it.height) }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan: Offset, zoom, _ ->
                        if (zoom != 1f) onZoomBy(zoom)
                        onDrag(pan.x, pan.y)
                    }
                }.semantics { contentDescription = description },
        ) {
            Image(
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier =
                    Modifier.fillMaxSize().graphicsLayer {
                        val current = framing
                        scaleX = current?.zoom ?: 1f
                        scaleY = current?.zoom ?: 1f
                        translationX = current?.offsetX ?: 0f
                        translationY = current?.offsetY ?: 0f
                    },
            )
            ThirdsGrid()
            ZoomControl(
                zoom = framing?.zoom ?: 1f,
                onZoomTo = onZoomTo,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = Tokens.space4),
            )
            Flash(flashes)
        }
        if (saveFailed) {
            Text(
                stringResource(R.string.photo_error),
                color = Tokens.error,
                modifier = Modifier.fillMaxWidth().padding(Tokens.space3),
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(
                    Tokens.shutter + Tokens.space6 * 2,
                ).background(Tokens.background)
                .padding(horizontal = Tokens.space6),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(Modifier.size(Tokens.minTouch)) {
                if (hasLastPhoto) {
                    TextButton(onClick = onShowLastPhoto, modifier = Modifier.size(Tokens.minTouch)) {
                        Text(
                            stringResource(R.string.last_photo_short),
                            color = Tokens.text,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
            ShutterButton(onShutter = onShutter, enabled = framing != null && !saving)
            Box(Modifier.size(Tokens.minTouch))
        }
    }
}

/** The shutter (DESIGN.md): a white 76 dp circle with a ring. The photo is only taken here (RN-0005). */
@Composable
private fun ShutterButton(
    onShutter: () -> Unit,
    enabled: Boolean,
) {
    val label = stringResource(R.string.take_photo)
    Box(
        Modifier
            .size(Tokens.shutter + Tokens.space3)
            .border(Tokens.space1, Tokens.text, CircleShape)
            .padding(Tokens.space2)
            .clip(CircleShape)
            .background(if (enabled) Tokens.text else Tokens.textSecondary)
            .clickable(enabled = enabled, onClickLabel = label, role = Role.Button, onClick = onShutter)
            .semantics { contentDescription = label },
    )
}

/** A short white flash when a photo is taken. */
@Composable
private fun Flash(flashes: Int) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(flashes) {
        if (flashes > 0) {
            alpha.snapTo(0.85f)
            alpha.animateTo(0f, tween(durationMillis = 300))
        }
    }
    if (alpha.value > 0f) Box(Modifier.fillMaxSize().background(Tokens.text.copy(alpha = alpha.value)))
}

@Composable
private fun ThirdsGrid() {
    Canvas(Modifier.fillMaxSize()) {
        val line = Tokens.text.copy(alpha = 0.25f)
        for (i in 1..2) {
            drawLine(line, Offset(size.width * i / 3, 0f), Offset(size.width * i / 3, size.height))
            drawLine(line, Offset(0f, size.height * i / 3), Offset(size.width, size.height * i / 3))
        }
    }
}

@Composable
private fun ZoomControl(
    zoom: Float,
    onZoomTo: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.background(Tokens.background.copy(alpha = 0.55f), RoundedCornerShape(Tokens.radius)),
        horizontalArrangement = Arrangement.spacedBy(Tokens.space1),
    ) {
        for (step in ZOOM_STEPS) {
            val active = kotlin.math.abs(zoom - step) < 0.01f
            val label = stringResource(R.string.zoom_step, step.toInt())
            TextButton(
                onClick = { onZoomTo(step) },
                modifier =
                    Modifier.sizeIn(minWidth = Tokens.minTouch, minHeight = Tokens.minTouch).semantics {
                        selected =
                            active
                    },
            ) {
                Text(
                    label,
                    color = if (active) Tokens.primary else Tokens.text,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}
