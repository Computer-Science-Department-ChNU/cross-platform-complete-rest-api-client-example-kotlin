package ua.edu.chnu.kkn.florilegium

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ua.edu.chnu.kkn.florilegium.ui.login.LoginScreen
import ua.edu.chnu.kkn.florilegium.ui.onboarding.OnboardingScreen
import ua.edu.chnu.kkn.florilegium.ui.theme.FlorilegiumTheme

@Serializable
data object Onboarding : NavKey

@Serializable
data object Login : NavKey

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Onboarding::class, Onboarding.serializer())
            subclass(Login::class, Login.serializer())
        }
    }
}

@Composable
@Preview
fun App() {
    FlorilegiumTheme {
        val appViewModel = koinViewModel<AppViewModel>()
        val backStack = rememberNavBackStack(navConfig, appViewModel.startDestination)
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Onboarding> {
                    OnboardingScreen(onFinishOnboarding = {
                        backStack.add(Login)
                        backStack.remove(Onboarding)
                    })
                }
                entry<Login> {
                    LoginScreen()
                }
            },
        )
    }
}
