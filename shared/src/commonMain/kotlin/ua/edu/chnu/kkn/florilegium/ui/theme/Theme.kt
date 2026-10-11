package ua.edu.chnu.kkn.florilegium.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

// Radius 4dp base, density 1.15× — the spacing scale below matches the
// --space-* tokens (4.6 / 9.2 / 13.8 / 18.4 / 27.6 / 36.8 px).

val ClassicalShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(7.dp),
    extraLarge = RoundedCornerShape(18.dp), // FAB
)

object Space {
    val x1 = 5.dp
    val x2 = 9.dp
    val x3 = 14.dp
    val x4 = 18.dp
    val x6 = 28.dp
    val x8 = 37.dp
}

val LocalAppColors = staticCompositionLocalOf { LightAppColors }

/** Design tokens that Material's [androidx.compose.material3.ColorScheme] has no slot for. */
object Florilegium {
    val colors: FlorilegiumColors
        @Composable @ReadOnlyComposable
        get() = LocalAppColors.current
}

@Composable
fun FlorilegiumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAppColors provides if (darkTheme) DarkAppColors else LightAppColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) ClassicalDark else ClassicalLight,
            typography = classicalTypography(),
            shapes = ClassicalShapes,
            content = content,
        )
    }
}
