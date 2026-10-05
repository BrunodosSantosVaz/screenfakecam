package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.ui.theme.Tokens

/** "Foto salva": the photo just taken, where and when it was saved, share it or take another. */
@Composable
fun SavedPhotoScreen(
    image: ImageBitmap?,
    savedAt: String,
    inGallery: Boolean,
    onShare: () -> Unit,
    onTakeAnother: () -> Unit,
    onBack: () -> Unit,
) {
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
            Text(stringResource(R.string.saved_title), style = MaterialTheme.typography.bodyLarge, color = Tokens.text)
        }
        Column(
            Modifier.weight(1f).fillMaxWidth().padding(Tokens.space5),
            verticalArrangement = Arrangement.spacedBy(Tokens.space4),
        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = stringResource(R.string.saved_title),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.weight(1f).fillMaxWidth().clip(RoundedCornerShape(Tokens.radius)),
                )
            }
            Text(
                stringResource(if (inGallery) R.string.saved_in_gallery else R.string.saved_in_place, savedAt),
                color = Tokens.success,
                style = MaterialTheme.typography.labelMedium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Tokens.space2), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onShare,
                    colors = ButtonDefaults.buttonColors(containerColor = Tokens.surface2, contentColor = Tokens.text),
                    modifier = Modifier.weight(1f).sizeIn(minHeight = Tokens.minTouch),
                ) { Text(stringResource(R.string.share)) }
                Button(
                    onClick = onTakeAnother,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Tokens.primary,
                            contentColor = Tokens.onPrimary,
                        ),
                    modifier = Modifier.weight(1f).sizeIn(minHeight = Tokens.minTouch),
                ) { Text(stringResource(R.string.take_another), fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}
