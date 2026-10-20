package ua.edu.chnu.kkn.florilegium.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.login_brand
import florilegium.shared.generated.resources.login_email_label
import florilegium.shared.generated.resources.login_password_hide
import florilegium.shared.generated.resources.login_password_label
import florilegium.shared.generated.resources.login_password_show
import florilegium.shared.generated.resources.login_sign_in
import florilegium.shared.generated.resources.login_subtitle
import florilegium.shared.generated.resources.login_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.edu.chnu.kkn.florilegium.ui.theme.Florilegium
import ua.edu.chnu.kkn.florilegium.ui.theme.Space

@Composable
fun LoginScreen(
    onSignedIn: (email: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val submit = {
        if (viewModel.signIn()) onSignedIn(state.email.trim())
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = Space.x6, end = Space.x6, bottom = Space.x8),
            verticalArrangement = Arrangement.spacedBy(Space.x6, Alignment.CenterVertically),
        ) {
            Column {
                Eyebrow()
                Text(
                    text = stringResource(Res.string.login_title),
                    modifier = Modifier.padding(vertical = Space.x2),
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 38.sp, lineHeight = 40.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(Res.string.login_subtitle),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 21.sp),
                    color = Florilegium.colors.muted,
                )
            }
            Spacer(Modifier.fillMaxWidth().height(1.dp).background(Florilegium.colors.line))
            LoginForm(
                state = state,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
                onSubmit = submit,
            )
            Column(verticalArrangement = Arrangement.spacedBy(Space.x3)) {
                SignInButton(
                    onClick = submit,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(14.dp),
                )
            }
        }
    }
}

@Composable
private fun Eyebrow() {
    Text(
        text = stringResource(Res.string.login_brand).uppercase(),
        style = MaterialTheme.typography.titleSmall.copy(fontSize = 11.sp, letterSpacing = 2.6.sp),
        color = Florilegium.colors.stroke,
    )
}

@Composable
private fun LoginForm(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Space.x4)) {
        LabeledField(
            label = stringResource(Res.string.login_email_label),
            value = state.email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            errorMessage = state.emailError?.let { stringResource(it) },
        )
        LabeledField(
            label = stringResource(Res.string.login_password_label),
            value = state.password,
            onValueChange = onPasswordChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onSubmit() }),
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            errorMessage = state.passwordError?.let { stringResource(it) },
            trailing = {
                TextButton(
                    onClick = onTogglePasswordVisibility,
                    contentPadding = PaddingValues(horizontal = Space.x2),
                ) {
                    Text(
                        text = stringResource(if (state.isPasswordVisible) Res.string.login_password_hide else Res.string.login_password_show).uppercase(),
                        style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.1.sp),
                        color = Florilegium.colors.muted,
                    )
                }
            },
        )
    }
}
@Composable
private fun LabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val isError = errorMessage != null
    Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.5.sp, letterSpacing = 1.5.sp),
            color = if (isError) MaterialTheme.colorScheme.error else Florilegium.colors.muted,
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            minLines = if (singleLine) 1 else 3,
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = trailing,
            isError = isError,
            supportingText = errorMessage?.let { { Text(it) } },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Florilegium.colors.stroke,
                unfocusedBorderColor = Florilegium.colors.line,
                cursorColor = Florilegium.colors.stroke,
            ),
        )
    }
}

@Composable
private fun SignInButton(
    onClick: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Florilegium.colors.stroke),
        contentPadding = contentPadding,
    ) {
        Text(
            text = stringResource(Res.string.login_sign_in).uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Florilegium.colors.accent,
        )
    }
}
