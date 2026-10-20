package ua.edu.chnu.kkn.florilegium.data.local.preferences

internal enum class PreferenceKey {
    IS_ONBOARDING_PASSED;

    val key get() = this.name
}
