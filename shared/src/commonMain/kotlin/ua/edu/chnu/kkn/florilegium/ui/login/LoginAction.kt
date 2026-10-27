package ua.edu.chnu.kkn.florilegium.ui.login

internal sealed interface LoginAction {
    data class ChangeEmail(val email: String) : LoginAction
    data class ChangePassword(val password: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object SignIn : LoginAction
}
