package ua.edu.chnu.kkn.florilegium.ui.onboarding

internal sealed class OnboardingEvent {
    data object OnOnboardingPassed : OnboardingEvent()
}
