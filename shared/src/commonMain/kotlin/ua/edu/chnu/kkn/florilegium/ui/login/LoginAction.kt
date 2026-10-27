package ua.edu.chnu.kkn.florilegium.ui.login

internal sealed class LoginAction {
    data class EmailChanged(val email: String) : LoginAction()
    data class PasswordChanged(val password: String) : LoginAction()
    data object TogglePasswordVisibility : LoginAction()
    data object SignIn : LoginAction()
}
