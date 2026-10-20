package ua.edu.chnu.kkn.florilegium.domain.onboarding

import ua.edu.chnu.kkn.florilegium.domain.PreferencesRepository

internal class MarkOnboardingPassedUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke() {
        preferencesRepository.setOnboardingPassed(true)
    }
}