package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
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
) {
    Column(Modifier.fillMaxSize().background(Tokens.background)) {
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
            )
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
        }
        Box(Modifier.fillMaxWidth().height(Tokens.shutter + Tokens.space6 * 2).background(Tokens.background))
    }
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
