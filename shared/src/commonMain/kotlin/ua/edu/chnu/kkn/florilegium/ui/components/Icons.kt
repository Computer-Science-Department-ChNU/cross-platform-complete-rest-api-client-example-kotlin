package ua.edu.chnu.kkn.florilegium.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/** Line icons from the design (24×24, round caps). Tinted by `Icon` through LocalContentColor. */
object AppIcons {
    val Refresh by lazy { lineIcon("Refresh", 1.5f, "M21 12a9 9 0 1 1-3-6.7L21 8", "M21 3v5h-5") }
    val Bookmark by lazy { lineIcon("Bookmark", 1.5f, "M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z") }
    val Share by lazy { lineIcon("Share", 1.5f, "M4 12v8h16v-8", "M12 16V3", "M8 7l4-4 4 4") }
    val Back by lazy { lineIcon("Back", 1.5f, "M19 12H5", "M12 19l-7-7 7-7") }
    val Search by lazy { lineIcon("Search", 1.6f, "M18 11a7 7 0 1 1-14 0a7 7 0 1 1 14 0", "M20 20l-4.2-4.2") }
    val Daily by lazy {
        lineIcon(
            "Daily", 1.5f,
            "M16 12a4 4 0 1 1-8 0a4 4 0 1 1 8 0",
            "M12 2v2M12 20v2M2 12h2M20 12h2M5 5l1.5 1.5M17.5 17.5L19 19M19 5l-1.5 1.5M6.5 17.5L5 19",
        )
    }
    val List by lazy { lineIcon("List", 1.5f, "M4 5h16M4 12h16M4 19h10") }
    val Spinner by lazy { lineIcon("Spinner", 1.6f, "M21 12a9 9 0 1 1-6.2-8.6") }
}

private fun lineIcon(name: String, strokeWidth: Float, vararg paths: String): ImageVector =
    ImageVector.Builder(name, 24.dp, 24.dp, 24f, 24f).apply {
        paths.forEach { d ->
            addPath(
                pathData = addPathNodes(d),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = strokeWidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
        }
    }.build()
