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

val ClassicalShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(7.dp),
    extraLarge = RoundedCornerShape(18.dp),
)

object Space {
    val x1 = 4.dp
    val x2 = 8.dp
    val x3 = 12.dp
    val x4 = 16.dp
    val x6 = 24.dp
    val x8 = 32.dp
}

private val LocalAppColors = staticCompositionLocalOf { LightAppColors }

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
