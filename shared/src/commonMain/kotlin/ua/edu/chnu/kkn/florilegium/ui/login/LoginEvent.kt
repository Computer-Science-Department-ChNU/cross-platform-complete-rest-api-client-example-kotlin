package ua.edu.chnu.kkn.florilegium.ui.login

internal sealed class LoginEvent {
    data object OnSignedIn : LoginEvent()
}
