package ua.edu.chnu.kkn.florilegium.domain.onboarding

import ua.edu.chnu.kkn.florilegium.domain.PreferencesRepository

internal class IsOnboardingPassedUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Boolean {
        return preferencesRepository.isOnboardingPassed()
    }
}
