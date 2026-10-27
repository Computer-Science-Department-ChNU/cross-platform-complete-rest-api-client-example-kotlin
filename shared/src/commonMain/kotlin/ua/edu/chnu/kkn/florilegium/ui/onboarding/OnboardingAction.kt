package ua.edu.chnu.kkn.florilegium.ui.onboarding

internal sealed interface OnboardingAction {
    data object CompleteOnboarding : OnboardingAction
}
