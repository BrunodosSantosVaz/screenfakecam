package io.github.brunodossantosvaz.screenfakecam.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import io.github.brunodossantosvaz.screenfakecam.R
import io.github.brunodossantosvaz.screenfakecam.domain.LinkPolicy
import io.github.brunodossantosvaz.screenfakecam.ui.theme.Tokens

/** Human name of a ZXing format: QR_CODE -> "QR code", EAN_13 -> "EAN-13". */
fun formatName(format: String): String =
    when (format) {
        "QR_CODE" -> "QR code"
        "DATA_MATRIX" -> "Data Matrix"
        "AZTEC" -> "Aztec"
        "PDF_417" -> "PDF417"
        "CODE_128" -> "Code 128"
        "CODE_39" -> "Code 39"
        "CODE_93" -> "Code 93"
        "CODABAR" -> "Codabar"
        else -> format.replace('_', '-')
    }

/**
 * "Código lido": the full text and type (RN-0006); Copy, Share and, only for http(s), Open link (RN-0007).
 * "Nenhum código encontrado" offers another image or framing the code in the viewfinder.
 */
@Composable
fun CodeResultScreen(
    outcome: CodeOutcome,
    onCopy: (String) -> Unit,
    onOpenLink: (String) -> Unit,
    onShare: (String) -> Unit,
    onReadAnother: () -> Unit,
    onFrameInViewfinder: () -> Unit,
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
            val title = if (outcome is CodeOutcome.Found) R.string.code_read_title else R.string.code_not_found_title
            Text(stringResource(title), style = MaterialTheme.typography.bodyLarge, color = Tokens.text)
        }
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(Tokens.space5),
            verticalArrangement = Arrangement.spacedBy(Tokens.space4),
        ) {
            when (outcome) {
                is CodeOutcome.Found -> Found(outcome, onCopy, onOpenLink, onShare, onReadAnother)
                CodeOutcome.NotFound -> NotFound(onReadAnother, onFrameInViewfinder)
            }
        }
    }
}

@Composable
private fun Found(
    outcome: CodeOutcome.Found,
    onCopy: (String) -> Unit,
    onOpenLink: (String) -> Unit,
    onShare: (String) -> Unit,
    onReadAnother: () -> Unit,
) {
    val text = outcome.code.text
    Column(
        Modifier.fillMaxWidth().background(Tokens.surface, RoundedCornerShape(Tokens.radius)).padding(Tokens.space4),
        verticalArrangement = Arrangement.spacedBy(Tokens.space2),
    ) {
        Text(
            formatName(outcome.code.format),
            color = Tokens.textSecondary,
            style = MaterialTheme.typography.labelMedium,
        )
        SelectionContainer { Text(text, color = Tokens.text, style = MaterialTheme.typography.bodyLarge) }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(Tokens.space2), modifier = Modifier.fillMaxWidth()) {
        Secondary(stringResource(R.string.copy), Modifier.weight(1f)) { onCopy(text) }
        if (LinkPolicy.canOpen(
                text,
            )
        ) {
            Secondary(stringResource(R.string.open_link), Modifier.weight(1f)) { onOpenLink(text) }
        }
        Secondary(stringResource(R.string.share), Modifier.weight(1f)) { onShare(text) }
    }
    Primary(stringResource(R.string.read_another), onReadAnother)
}

@Composable
private fun NotFound(
    onReadAnother: () -> Unit,
    onFrameInViewfinder: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Tokens.surface, RoundedCornerShape(Tokens.radius))
            .padding(Tokens.space4)
            .semantics { liveRegion = LiveRegionMode.Polite },
        verticalArrangement = Arrangement.spacedBy(Tokens.space2),
    ) {
        Text(stringResource(R.string.code_not_found_title), color = Tokens.error, fontWeight = FontWeight.SemiBold)
        Text(stringResource(R.string.code_not_found_help), color = Tokens.textSecondary)
    }
    Row(horizontalArrangement = Arrangement.spacedBy(Tokens.space2), modifier = Modifier.fillMaxWidth()) {
        Secondary(stringResource(R.string.choose_another), Modifier.weight(1f), onReadAnother)
        Button(
            onClick = onFrameInViewfinder,
            colors = ButtonDefaults.buttonColors(containerColor = Tokens.primary, contentColor = Tokens.onPrimary),
            modifier = Modifier.weight(1f).sizeIn(minHeight = Tokens.minTouch),
        ) { Text(stringResource(R.string.frame_in_viewfinder), fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
private fun Secondary(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Tokens.surface2, contentColor = Tokens.text),
        modifier = modifier.sizeIn(minHeight = Tokens.minTouch),
    ) { Text(label) }
}

@Composable
private fun Primary(
    label: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Tokens.primary, contentColor = Tokens.onPrimary),
        modifier = Modifier.fillMaxWidth().sizeIn(minHeight = Tokens.minTouch),
    ) { Text(label, fontWeight = FontWeight.SemiBold) }
}
