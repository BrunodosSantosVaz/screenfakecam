package io.github.brunodossantosvaz.screenfakecam.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Design kit tokens (DESIGN.md, docs/design/tokens.css). UI code uses these, never literal values (FE-01). */
object Tokens {
    val background = Color(0xFF000000)
    val surface = Color(0xFF1C1C1E)
    val surface2 = Color(0xFF2C2C2E)
    val text = Color(0xFFFFFFFF)
    val textSecondary = Color(0xFFB3B3B3)
    val primary = Color(0xFFFFC107)
    val onPrimary = Color(0xFF000000)
    val error = Color(0xFFFF6B6B)
    val success = Color(0xFF4ADE80)

    val space1 = 4.dp
    val space2 = 8.dp
    val space3 = 12.dp
    val space4 = 16.dp
    val space5 = 24.dp
    val space6 = 32.dp
    val radius = 12.dp
    val minTouch = 48.dp
    val shutter = 76.dp
}

private val colors =
    darkColorScheme(
        primary = Tokens.primary,
        onPrimary = Tokens.onPrimary,
        background = Tokens.background,
        onBackground = Tokens.text,
        surface = Tokens.surface,
        onSurface = Tokens.text,
        surfaceVariant = Tokens.surface2,
        onSurfaceVariant = Tokens.textSecondary,
        error = Tokens.error,
    )

private val typography =
    Typography(
        titleLarge = TextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.SemiBold),
        bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
        labelMedium = TextStyle(fontSize = 13.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium),
    )

/** Always dark: the app looks like a real camera (DESIGN.md). */
@Composable
fun ScreenFakeCamTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colors, typography = typography, content = content)
}
