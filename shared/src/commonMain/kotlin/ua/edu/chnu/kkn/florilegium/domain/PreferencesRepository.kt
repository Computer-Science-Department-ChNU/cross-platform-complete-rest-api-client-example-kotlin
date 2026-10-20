package ua.edu.chnu.kkn.florilegium.domain

internal interface PreferencesRepository {
    fun isOnboardingPassed(): Boolean
    fun setOnboardingPassed(passed: Boolean)
}
