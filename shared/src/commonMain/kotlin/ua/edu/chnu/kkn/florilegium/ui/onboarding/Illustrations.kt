package ua.edu.chnu.kkn.florilegium.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.florilegium.ui.theme.Florilegium

// The four onboarding plates (export/vector-xml in the design project), drawn in the
// accent stroke with one faint tinted fill each so they read the same in both schemes.

private class Stroke(val d: String, val alpha: Float = 1f, val width: Float = 1.2f, val filled: Boolean = false)

private val Plates: List<List<Stroke>> = listOf(
    // 1 · commonplace book
    listOf(
        Stroke("M130 62c-22-13-48-16-70-12v134c22-4 48-1 70 12 22-13 48-16 70-12V50c-22-4-48-1-70 12z"),
        Stroke("M130 62v134"),
        Stroke("M76 92h38M76 108h38M76 124h30M146 92h38M146 108h38M146 124h30", alpha = 0.45f),
        Stroke("M130 50v-26M130 24l-14 10M130 24l14 10", alpha = 0.55f),
        Stroke("M143 150a13 13 0 1 1-26 0a13 13 0 1 1 26 0", alpha = 0.8f),
        Stroke("M124 150l5 5 8-11", alpha = 0.8f),
    ),
    // 2 · daily draw
    listOf(
        Stroke("M160 78a30 30 0 1 1-60 0a30 30 0 1 1 60 0"),
        Stroke("M130 26v-14M130 144v14M78 78h-14M196 78h14M93 41l-10-10M167 41l10-10M93 115l-10 10M167 115l10 10", alpha = 0.6f),
        Stroke("M65 132h130a3 3 0 0 1 3 3v92a3 3 0 0 1-3 3H65a3 3 0 0 1-3-3v-92a3 3 0 0 1 3-3z"),
        Stroke("M62 160h136", alpha = 0.5f),
        Stroke("M82 180h96M82 196h96M82 212h58", alpha = 0.45f),
        Stroke("M76 152c0-6 3-9 8-10M88 152c0-6 3-9 8-10", alpha = 0.7f),
    ),
    // 3 · search the archive
    listOf(
        Stroke("M47 34h166a3 3 0 0 1 3 3v186a3 3 0 0 1-3 3H47a3 3 0 0 1-3-3V37a3 3 0 0 1 3-3z", alpha = 0.8f),
        Stroke("M66 66h128M66 88h128M66 110h96M66 154h128M66 176h128M66 198h84", alpha = 0.4f),
        Stroke("M194 132a44 44 0 1 1-88 0a44 44 0 1 1 88 0", filled = true),
        Stroke("M181 163l28 28", width = 2f),
        Stroke("M132 132h36M132 144h22", alpha = 0.7f),
    ),
    // 4 · every screen
    listOf(
        Stroke("M40 72h120a6 6 0 0 1 6 6v86a6 6 0 0 1-6 6H40a6 6 0 0 1-6-6V78a6 6 0 0 1 6-6z"),
        Stroke("M34 96h132", alpha = 0.5f),
        Stroke("M52 116h44M52 132h44M52 148h30M112 116h38M112 132h38", alpha = 0.4f),
        Stroke("M160 104h56a10 10 0 0 1 10 10v112a10 10 0 0 1-10 10h-56a10 10 0 0 1-10-10V114a10 10 0 0 1 10-10z", filled = true),
        Stroke("M150 128h76", alpha = 0.5f),
        Stroke("M166 148h44M166 164h44M166 180h28", alpha = 0.4f),
        Stroke("M178 222h20", alpha = 0.6f),
        Stroke("M197 88a9 9 0 1 1-18 0a9 9 0 1 1 18 0", alpha = 0.7f),
        Stroke("M188 79v-14M181 70l7-5 7 5", alpha = 0.5f),
    ),
)

val PlateCount get() = Plates.size

/** Plate [index] (0-based, in slide order) in the current scheme's stroke and tint. */
@Composable
fun OnboardingPlate(index: Int, modifier: Modifier = Modifier) {
    val colors = Florilegium.colors
    val vector = remember(index, colors.stroke, colors.soft) { onboardingPlate(index, colors.stroke, colors.soft) }
    Image(rememberVectorPainter(vector), contentDescription = null, modifier = modifier)
}

fun onboardingPlate(index: Int, stroke: Color, tint: Color): ImageVector =
    ImageVector.Builder("onboarding_$index", 260.dp, 260.dp, 260f, 260f).apply {
        Plates[index].forEach { s ->
            addPath(
                pathData = addPathNodes(s.d),
                fill = if (s.filled) SolidColor(tint) else null,
                stroke = SolidColor(stroke),
                strokeAlpha = s.alpha,
                strokeLineWidth = s.width,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
        }
    }.build()
