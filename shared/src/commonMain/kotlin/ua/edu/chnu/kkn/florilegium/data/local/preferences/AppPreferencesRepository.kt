package ua.edu.chnu.kkn.florilegium.data.local.preferences

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import ua.edu.chnu.kkn.florilegium.domain.PreferencesRepository

internal class AppPreferencesRepository(
    val settings: Settings,
) : PreferencesRepository {

    override fun isOnboardingPassed(): Boolean {
        return settings.get<Boolean>(PreferenceKey.IS_ONBOARDING_PASSED.key) ?: false
    }

    override fun setOnboardingPassed(passed: Boolean) {
        settings[PreferenceKey.IS_ONBOARDING_PASSED.key] = passed
    }
}
