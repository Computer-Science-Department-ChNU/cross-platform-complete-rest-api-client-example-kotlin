package ua.edu.chnu.kkn.florilegium

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ua.edu.chnu.kkn.florilegium.di.initKoin

fun main() = application {
    initKoin { printLogger() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Florilegium",
    ) {
        App()
    }
}
