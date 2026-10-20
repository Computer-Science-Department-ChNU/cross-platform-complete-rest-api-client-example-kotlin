package ua.edu.chnu.kkn.florilegium

import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.login_error_email_empty
import florilegium.shared.generated.resources.login_error_email_invalid
import florilegium.shared.generated.resources.login_error_password_empty
import florilegium.shared.generated.resources.login_error_password_no_digit
import florilegium.shared.generated.resources.login_error_password_no_letter
import florilegium.shared.generated.resources.login_error_password_spaces
import florilegium.shared.generated.resources.login_error_password_too_short
import ua.edu.chnu.kkn.florilegium.ui.login.LoginValidator
import ua.edu.chnu.kkn.florilegium.ui.login.LoginViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoginValidatorTest {

    @Test
    fun acceptsValidEmails() {
        listOf("name@example.com", "first.last+tag@sub.domain.ua", " padded@example.org ").forEach {
            assertNull(LoginValidator.validateEmail(it), it)
        }
    }

    @Test
    fun rejectsInvalidEmails() {
        assertEquals(Res.string.login_error_email_empty, LoginValidator.validateEmail(""))
        listOf("plain", "no-at.example.com", "a@b", "a@b.c", "a b@example.com", "a@@example.com").forEach {
            assertEquals(Res.string.login_error_email_invalid, LoginValidator.validateEmail(it), it)
        }
    }

    @Test
    fun reportsFirstBrokenPasswordRule() {
        assertEquals(Res.string.login_error_password_empty, LoginValidator.validatePassword(""))
        assertEquals(Res.string.login_error_password_spaces, LoginValidator.validatePassword("abc 12345"))
        assertEquals(Res.string.login_error_password_too_short, LoginValidator.validatePassword("abc123"))
        assertEquals(Res.string.login_error_password_no_letter, LoginValidator.validatePassword("12345678"))
        assertEquals(Res.string.login_error_password_no_digit, LoginValidator.validatePassword("abcdefgh"))
        assertNull(LoginValidator.validatePassword("commonplace1"))
    }
}

class LoginViewModelTest {

    @Test
    fun typingDoesNotValidateBeforeFirstSubmit() {
        val vm = LoginViewModel()
        vm.onEmailChange("not-an-email")
        vm.onPasswordChange("x")

        assertNull(vm.uiState.value.emailError)
        assertNull(vm.uiState.value.passwordError)
    }

    @Test
    fun submitShowsSeparateErrorsForEachField() {
        val vm = LoginViewModel()
        vm.onEmailChange("not-an-email")
        vm.onPasswordChange("short1")

        assertFalse(vm.signIn())
        assertNotNull(vm.uiState.value.emailError)
        assertEquals(Res.string.login_error_password_too_short, vm.uiState.value.passwordError)
    }

    @Test
    fun errorDisappearsOnceFieldBecomesValid() {
        val vm = LoginViewModel()
        vm.onEmailChange("name@example")
        vm.onPasswordChange("commonplace1")
        vm.signIn()
        assertNotNull(vm.uiState.value.emailError)
        assertNull(vm.uiState.value.passwordError)

        vm.onEmailChange("name@example.com")
        assertNull(vm.uiState.value.emailError)

        // A field that was valid at submit time doesn't start showing errors while typing.
        vm.onPasswordChange("x")
        assertNull(vm.uiState.value.passwordError)
    }

    @Test
    fun errorMessageFollowsTheCurrentlyBrokenRule() {
        val vm = LoginViewModel()
        vm.signIn()
        assertEquals(Res.string.login_error_password_empty, vm.uiState.value.passwordError)

        vm.onPasswordChange("abcdefgh")
        assertEquals(Res.string.login_error_password_no_digit, vm.uiState.value.passwordError)
    }

    @Test
    fun signInSucceedsWithValidInput() {
        val vm = LoginViewModel()
        vm.onEmailChange("elena.voitenko@chnu.edu.ua")
        vm.onPasswordChange("commonplace1")

        assertTrue(vm.signIn())
        assertNull(vm.uiState.value.emailError)
        assertNull(vm.uiState.value.passwordError)
    }
}
