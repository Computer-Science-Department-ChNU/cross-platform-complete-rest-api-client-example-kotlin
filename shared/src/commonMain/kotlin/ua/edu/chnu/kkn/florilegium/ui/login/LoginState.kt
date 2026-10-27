package ua.edu.chnu.kkn.florilegium.ui.login

import org.jetbrains.compose.resources.StringResource

internal data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
)
