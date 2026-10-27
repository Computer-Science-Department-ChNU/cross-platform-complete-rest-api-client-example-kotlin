package ua.edu.chnu.kkn.florilegium.ui.common

import androidx.compose.ui.tooling.preview.Preview

// Configuration.UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL; android.content.res isn't available in commonMain.
private const val UiModeNight = 0x21

/** Wide window (above the two-pane breakpoint) in light and dark, like `@PreviewLightDark` for phones. */
@Preview(name = "Two-pane - Light", widthDp = 1000, heightDp = 640)
@Preview(name = "Two-pane - Dark", widthDp = 1000, heightDp = 640, uiMode = UiModeNight)
annotation class PreviewTwoPaneLightDark
