package ua.edu.chnu.kkn.florilegium.ui.login

import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.login_error_email_empty
import florilegium.shared.generated.resources.login_error_email_invalid
import florilegium.shared.generated.resources.login_error_password_empty
import florilegium.shared.generated.resources.login_error_password_no_digit
import florilegium.shared.generated.resources.login_error_password_no_letter
import florilegium.shared.generated.resources.login_error_password_spaces
import florilegium.shared.generated.resources.login_error_password_too_short
import org.jetbrains.compose.resources.StringResource

internal object LoginValidator {
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
