package ua.edu.chnu.kkn.florilegium.ui.onboarding

internal sealed interface OnboardingEvent {
    data object OnOnboardingPassed : OnboardingEvent
}
