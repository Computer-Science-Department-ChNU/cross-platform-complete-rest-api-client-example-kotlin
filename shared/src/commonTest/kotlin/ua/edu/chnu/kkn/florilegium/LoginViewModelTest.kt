package ua.edu.chnu.kkn.florilegium

import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.login_error_email_empty
import florilegium.shared.generated.resources.login_error_email_invalid
import florilegium.shared.generated.resources.login_error_password_empty
import florilegium.shared.generated.resources.login_error_password_no_digit
import florilegium.shared.generated.resources.login_error_password_no_letter
import florilegium.shared.generated.resources.login_error_password_spaces
import florilegium.shared.generated.resources.login_error_password_too_short
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import ua.edu.chnu.kkn.florilegium.ui.login.LoginAction
import ua.edu.chnu.kkn.florilegium.ui.login.LoginEvent
import ua.edu.chnu.kkn.florilegium.ui.login.LoginValidator
import ua.edu.chnu.kkn.florilegium.ui.login.LoginViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
        vm.onAction(LoginAction.ChangeEmail("not-an-email"))
        vm.onAction(LoginAction.ChangePassword("x"))

        assertNull(vm.state.value.emailError)
        assertNull(vm.state.value.passwordError)
    }

    @Test
    fun submitShowsSeparateErrorsForEachField() = runTest {
        val vm = LoginViewModel()
        vm.onAction(LoginAction.ChangeEmail("not-an-email"))
        vm.onAction(LoginAction.ChangePassword("short1"))
        vm.onAction(LoginAction.SignIn)

        assertNotNull(vm.state.value.emailError)
        assertEquals(Res.string.login_error_password_too_short, vm.state.value.passwordError)
        assertNull(withTimeoutOrNull(1) { vm.events.first() })
    }

    @Test
    fun errorDisappearsOnceFieldBecomesValid() {
        val vm = LoginViewModel()
        vm.onAction(LoginAction.ChangeEmail("name@example"))
        vm.onAction(LoginAction.ChangePassword("commonplace1"))
        vm.onAction(LoginAction.SignIn)
        assertNotNull(vm.state.value.emailError)
        assertNull(vm.state.value.passwordError)

        vm.onAction(LoginAction.ChangeEmail("name@example.com"))
        assertNull(vm.state.value.emailError)

        // A field that was valid at submit time doesn't start showing errors while typing.
        vm.onAction(LoginAction.ChangePassword("x"))
        assertNull(vm.state.value.passwordError)
    }

    @Test
    fun errorMessageFollowsTheCurrentlyBrokenRule() {
        val vm = LoginViewModel()
        vm.onAction(LoginAction.SignIn)
        assertEquals(Res.string.login_error_password_empty, vm.state.value.passwordError)

        vm.onAction(LoginAction.ChangePassword("abcdefgh"))
        assertEquals(Res.string.login_error_password_no_digit, vm.state.value.passwordError)
    }

    @Test
    fun togglesPasswordVisibility() {
        val vm = LoginViewModel()
        vm.onAction(LoginAction.TogglePasswordVisibility)
        assertEquals(true, vm.state.value.isPasswordVisible)

        vm.onAction(LoginAction.TogglePasswordVisibility)
        assertEquals(false, vm.state.value.isPasswordVisible)
    }

    @Test
    fun signInEmitsSignedInEventWithValidInput() = runTest {
        val vm = LoginViewModel()
        vm.onAction(LoginAction.ChangeEmail(" elena.voitenko@chnu.edu.ua "))
        vm.onAction(LoginAction.ChangePassword("commonplace1"))
        vm.onAction(LoginAction.SignIn)

        assertNull(vm.state.value.emailError)
        assertNull(vm.state.value.passwordError)
        assertIs<LoginEvent.OnSignedIn>(vm.events.first())
    }
}
