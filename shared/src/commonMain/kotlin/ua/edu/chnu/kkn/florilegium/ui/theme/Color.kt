package ua.edu.chnu.kkn.florilegium.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ── Classical tokens ────────────────────────────────────────────────
// Ground / ink / single gold accent + the 100–900 ramps.

val Bg = Color(0xFFF3F2F2)
val Surface = Color(0xFFFBFAF9)
val SurfaceMuted = Color(0xFFEAE9E9)
val Ink = Color(0xFF201F1D)
val Accent = Color(0xFFB68235)

val Neutral100 = Color(0xFFF8F4F4)
val Neutral200 = Color(0xFFEAE7E7)
val Neutral300 = Color(0xFFD7D3D3)
val Neutral400 = Color(0xFFBAB6B6)
val Neutral500 = Color(0xFF9B9797)
val Neutral600 = Color(0xFF7D7979)
val Neutral700 = Color(0xFF605D5D)
val Neutral800 = Color(0xFF444141)
val Neutral900 = Color(0xFF2D2B2B)

val Accent100 = Color(0xFFFFF3E4)
val Accent200 = Color(0xFFFFE3BF)
val Accent300 = Color(0xFFFACB8D)
val Accent400 = Color(0xFFE1AD66)
val Accent500 = Color(0xFFC28D41)
val Accent600 = Color(0xFFA06F24)
val Accent700 = Color(0xFF7D5411)
val Accent800 = Color(0xFF5A3B0A)
val Accent900 = Color(0xFF3A270D)

// Dark ground (warm near-black, accent stepped up so gold keeps contrast)
val DarkBg = Color(0xFF171614)
val DarkSurface = Color(0xFF201F1D)
val DarkSurfaceMuted = Color(0xFF2D2B2B)
val DarkInk = Color(0xFFF1EFEC)

// ── Material 3 schemes ──────────────────────────────────────────────
// Accent is stroke-only in this system: primary is used for outlines,
// icons and text, never as a filled container. Containers stay on the
// ground, so primaryContainer is the faintest accent tint.

val ClassicalLight = lightColorScheme(
    primary = Accent700,
    onPrimary = Bg,
    primaryContainer = Accent100,
    onPrimaryContainer = Accent800,
    secondary = Neutral700,
    onSecondary = Bg,
    secondaryContainer = Neutral200,
    onSecondaryContainer = Neutral900,
    tertiary = Accent600,
    onTertiary = Bg,
    background = Bg,
    onBackground = Ink,
    surface = Surface,
    onSurface = Ink,
    surfaceVariant = SurfaceMuted,
    onSurfaceVariant = Neutral700,
    outline = Accent,
    outlineVariant = Neutral300,
    error = Color(0xFF8C2F1F),
    onError = Bg,
)

val ClassicalDark = darkColorScheme(
    primary = Accent300,
    onPrimary = DarkBg,
    primaryContainer = Accent900,
    onPrimaryContainer = Accent200,
    secondary = Neutral400,
    onSecondary = DarkBg,
    secondaryContainer = Neutral800,
    onSecondaryContainer = Neutral200,
    tertiary = Accent400,
    onTertiary = DarkBg,
    background = DarkBg,
    onBackground = DarkInk,
    surface = DarkSurface,
    onSurface = DarkInk,
    surfaceVariant = DarkSurfaceMuted,
    onSurfaceVariant = Neutral400,
    outline = Accent500,
    outlineVariant = Color(0x33F1EFEC),
    error = Color(0xFFE08C7A),
    onError = DarkBg,
)

// ── App tokens ──────────────────────────────────────────────────────
// The --app-* variables from the design: hairlines instead of elevation,
// gold as stroke, a faint accent wash for the few tinted surfaces.

data class FlorilegiumColors(
    val accent: Color,
    val stroke: Color,
    val soft: Color,
    val muted: Color,
    val line: Color,
    val hair: Color,
)

val LightAppColors = FlorilegiumColors(
    accent = Accent700,
    stroke = Accent,
    soft = Accent100,
    muted = Neutral700,
    line = Ink.copy(alpha = 0.16f),
    hair = Ink.copy(alpha = 0.08f),
)

val DarkAppColors = FlorilegiumColors(
    accent = Accent300,
    stroke = Accent500,
    soft = Accent400.copy(alpha = 0.12f),
    muted = Neutral400,
    line = DarkInk.copy(alpha = 0.20f),
    hair = DarkInk.copy(alpha = 0.10f),
)
