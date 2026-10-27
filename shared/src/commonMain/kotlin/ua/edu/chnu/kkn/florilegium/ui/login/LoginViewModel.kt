package ua.edu.chnu.kkn.florilegium.ui.login

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

@Stable
internal class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _events = Channel<LoginEvent>(capacity = Channel.BUFFERED)
    val events: Flow<LoginEvent> = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.ChangeEmail -> _state.update {
                it.copy(email = action.email, emailError = it.emailError?.let { LoginValidator.validateEmail(action.email) })
            }
            is LoginAction.ChangePassword -> _state.update {
                it.copy(password = action.password, passwordError = it.passwordError?.let { LoginValidator.validatePassword(action.password) })
            }
            is LoginAction.TogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is LoginAction.SignIn -> signIn()
        }
    }

    private fun signIn() {
        val state = _state.updateAndGet {
            it.copy(
                emailError = LoginValidator.validateEmail(it.email),
                passwordError = LoginValidator.validatePassword(it.password),
            )
        }
        if (credentialsAreValid(state)) {
            _events.trySend(LoginEvent.OnSignedIn)
        }
    }

    private fun credentialsAreValid(state: LoginState): Boolean {
        return state.emailError == null && state.passwordError == null
    }
}
