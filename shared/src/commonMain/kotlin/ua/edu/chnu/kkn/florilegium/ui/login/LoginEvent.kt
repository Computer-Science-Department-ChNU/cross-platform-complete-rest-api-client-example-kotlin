package ua.edu.chnu.kkn.florilegium.ui.login

internal sealed interface LoginEvent {
    data object OnSignedIn : LoginEvent
}
