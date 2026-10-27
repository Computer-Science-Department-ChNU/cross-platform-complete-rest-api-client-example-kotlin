package ua.edu.chnu.kkn.florilegium.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import florilegium.shared.generated.resources.login_quote
import florilegium.shared.generated.resources.login_quote_author
import florilegium.shared.generated.resources.login_sign_in
import florilegium.shared.generated.resources.login_subtitle
import florilegium.shared.generated.resources.login_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.edu.chnu.kkn.florilegium.ui.common.CenteredScrollColumn
import ua.edu.chnu.kkn.florilegium.ui.common.ObserveEvents
import ua.edu.chnu.kkn.florilegium.ui.common.isTwoPaneLayout
import ua.edu.chnu.kkn.florilegium.ui.theme.Florilegium
import ua.edu.chnu.kkn.florilegium.ui.theme.Space

private const val EpigraphPaneWeight = 0.46f
private val EpigraphMaxWidth = 440.dp
private val FormMaxWidth = 520.dp

@Composable
internal fun LoginScreen(
    onSignedIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.OnSignedIn -> onSignedIn()
        }
    }

    val login = { viewModel.onAction(LoginAction.SignIn) }

    val loginForm = @Composable {
        LoginForm(
            state = state,
            onEmailChange = { viewModel.onAction(LoginAction.EmailChanged(it)) },
            onPasswordChange = { viewModel.onAction(LoginAction.PasswordChanged(it)) },
            onTogglePasswordVisibility = { viewModel.onAction(LoginAction.TogglePasswordVisibility) },
            login = login,
        )
    }

    if (isTwoPaneLayout()) {
        LoginTwoPane(loginForm = loginForm, login = login, modifier = modifier)
    } else {
        LoginSinglePane(loginForm = loginForm, login = login, modifier = modifier)
    }
}

@Composable
private fun LoginSinglePane(
    loginForm: @Composable () -> Unit,
    login: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenteredScrollColumn(
        spacing = Space.x6,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding(),
        contentPadding = PaddingValues(start = Space.x6, end = Space.x6, bottom = Space.x8),
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
        loginForm()
        SignInButton(
            onClick = login,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(14.dp),
        )
    }
}

@Composable
private fun LoginTwoPane(
    loginForm: @Composable () -> Unit,
    login: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .weight(EpigraphPaneWeight)
                .fillMaxHeight()
                .background(Florilegium.colors.soft)
                .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Start + WindowInsetsSides.Vertical))
                .padding(Space.x8),
            verticalArrangement = Arrangement.spacedBy(Space.x4, Alignment.CenterVertically),
        ) {
            Eyebrow()
            Text(
                text = stringResource(Res.string.login_quote),
                modifier = Modifier.widthIn(max = EpigraphMaxWidth),
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 38.sp, lineHeight = 48.sp),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(Res.string.login_quote_author).uppercase(),
                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 2.sp),
                color = Florilegium.colors.muted,
            )
        }
        Spacer(Modifier.fillMaxHeight().width(1.dp).background(Florilegium.colors.line))
        Box(
            modifier = Modifier
                .weight(1f - EpigraphPaneWeight)
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.End + WindowInsetsSides.Vertical)),
        ) {
            CenteredScrollColumn(
                spacing = Space.x6,
                modifier = Modifier.widthIn(max = FormMaxWidth),
                contentPadding = PaddingValues(Space.x8),
            ) {
                Text(
                    text = stringResource(Res.string.login_title),
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 40.sp, lineHeight = 44.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                loginForm()
                SignInButton(
                    onClick = login,
                    contentPadding = PaddingValues(horizontal = 34.dp, vertical = 13.dp),
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
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    login: () -> Unit,
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
            keyboardActions = KeyboardActions(onDone = { login() }),
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
