package ua.edu.chnu.kkn.florilegium

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import ua.edu.chnu.kkn.florilegium.domain.onboarding.IsOnboardingPassedUseCase

@Stable
internal class AppViewModel(
    isOnboardingPassedUseCase: IsOnboardingPassedUseCase
) : ViewModel() {

    val startDestination: NavKey = if (isOnboardingPassedUseCase()) Login else Onboarding
}
