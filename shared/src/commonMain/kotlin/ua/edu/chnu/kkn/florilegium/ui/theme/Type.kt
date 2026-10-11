package ua.edu.chnu.kkn.florilegium.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.cormorant_garamond_light
import florilegium.shared.generated.resources.cormorant_garamond_medium
import florilegium.shared.generated.resources.cormorant_garamond_regular
import florilegium.shared.generated.resources.cormorant_garamond_semibold
import florilegium.shared.generated.resources.lora_italic
import florilegium.shared.generated.resources.lora_medium
import florilegium.shared.generated.resources.lora_regular
import org.jetbrains.compose.resources.Font

// Cormorant Garamond → headings · Lora → body. No sans-serif, no bold:
// semibold (600) is the ceiling for interface headings; display sizes
// set lighter (300–400).

@Composable
fun headingFamily() = FontFamily(
    Font(Res.font.cormorant_garamond_light, FontWeight.Light),
    Font(Res.font.cormorant_garamond_regular, FontWeight.Normal),
    Font(Res.font.cormorant_garamond_medium, FontWeight.Medium),
    Font(Res.font.cormorant_garamond_semibold, FontWeight.SemiBold),
)

@Composable
fun bodyFamily() = FontFamily(
    Font(Res.font.lora_regular, FontWeight.Normal),
    Font(Res.font.lora_medium, FontWeight.Medium),
    Font(Res.font.lora_italic, FontWeight.Normal, FontStyle.Italic),
)

@Composable
fun classicalTypography(): Typography {
    val heading = headingFamily()
    val body = bodyFamily()
    return Typography(
        displayLarge = TextStyle(fontFamily = heading, fontWeight = FontWeight.Light, fontSize = 52.sp, lineHeight = 56.sp),
        displayMedium = TextStyle(fontFamily = heading, fontWeight = FontWeight.Light, fontSize = 42.sp, lineHeight = 50.sp),
        displaySmall = TextStyle(fontFamily = heading, fontWeight = FontWeight.Normal, fontSize = 34.sp, lineHeight = 40.sp),
        headlineLarge = TextStyle(fontFamily = heading, fontWeight = FontWeight.Normal, fontSize = 30.sp, lineHeight = 36.sp),
        headlineMedium = TextStyle(fontFamily = heading, fontWeight = FontWeight.Normal, fontSize = 26.sp, lineHeight = 32.sp),
        headlineSmall = TextStyle(fontFamily = heading, fontWeight = FontWeight.Medium, fontSize = 23.sp, lineHeight = 28.sp),
        titleLarge = TextStyle(fontFamily = heading, fontWeight = FontWeight.Medium, fontSize = 20.sp, lineHeight = 26.sp),
        titleMedium = TextStyle(fontFamily = heading, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 22.sp),
        titleSmall = TextStyle(fontFamily = heading, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 1.4.sp),
        bodyLarge = TextStyle(fontFamily = body, fontSize = 15.sp, lineHeight = 25.sp),
        bodyMedium = TextStyle(fontFamily = body, fontSize = 14.sp, lineHeight = 23.sp),
        bodySmall = TextStyle(fontFamily = body, fontSize = 12.5.sp, lineHeight = 20.sp),
        labelLarge = TextStyle(fontFamily = heading, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 1.4.sp),
        labelMedium = TextStyle(fontFamily = body, fontSize = 11.sp, letterSpacing = 1.5.sp),
        labelSmall = TextStyle(fontFamily = body, fontSize = 10.sp, letterSpacing = 1.8.sp),
    )
}
