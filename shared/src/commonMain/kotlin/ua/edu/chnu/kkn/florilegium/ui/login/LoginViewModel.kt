package ua.edu.chnu.kkn.florilegium.ui.login

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.login_error_email_empty
import florilegium.shared.generated.resources.login_error_email_invalid
import florilegium.shared.generated.resources.login_error_password_empty
import florilegium.shared.generated.resources.login_error_password_no_digit
import florilegium.shared.generated.resources.login_error_password_no_letter
import florilegium.shared.generated.resources.login_error_password_spaces
import florilegium.shared.generated.resources.login_error_password_too_short
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import org.jetbrains.compose.resources.StringResource

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val emailError: StringResource? = null,
    val passwordError: StringResource? = null,
)

object LoginValidator {
    private val EmailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$")
    private val WhitespaceRegex = Regex("\\s")
    private val MinLengthRegex = Regex("^.{8,}$")
    private val LetterRegex = Regex("[A-Za-z]")
    private val DigitRegex = Regex("\\d")

    fun validateEmail(email: String): StringResource? = when {
        email.isBlank() -> Res.string.login_error_email_empty
        !EmailRegex.matches(email.trim()) -> Res.string.login_error_email_invalid
        else -> null
    }

    fun validatePassword(password: String): StringResource? = when {
        password.isEmpty() -> Res.string.login_error_password_empty
        WhitespaceRegex.containsMatchIn(password) -> Res.string.login_error_password_spaces
        !MinLengthRegex.matches(password) -> Res.string.login_error_password_too_short
        !LetterRegex.containsMatchIn(password) -> Res.string.login_error_password_no_letter
        !DigitRegex.containsMatchIn(password) -> Res.string.login_error_password_no_digit
        else -> null
    }
}

@Stable
class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Typing never raises a new error; it only re-checks a field that already shows one,
    // so the message updates and disappears as soon as the rules are met.
    fun onEmailChange(email: String) = _uiState.update {
        it.copy(email = email, emailError = it.emailError?.let { LoginValidator.validateEmail(email) })
    }

    fun onPasswordChange(password: String) = _uiState.update {
        it.copy(password = password, passwordError = it.passwordError?.let { LoginValidator.validatePassword(password) })
    }

    fun onTogglePasswordVisibility() = _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }

    fun signIn(): Boolean {
        val state = _uiState.updateAndGet {
            it.copy(
                emailError = LoginValidator.validateEmail(it.email),
                passwordError = LoginValidator.validatePassword(it.password),
            )
        }
        return state.emailError == null && state.passwordError == null
    }
}
