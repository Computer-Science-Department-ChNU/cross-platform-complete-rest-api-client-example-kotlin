package ua.edu.chnu.kkn.florilegium.di

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import org.koin.plugin.module.dsl.create
import org.koin.plugin.module.dsl.viewModel
import ua.edu.chnu.kkn.florilegium.AppViewModel
import ua.edu.chnu.kkn.florilegium.data.local.preferences.AppPreferencesRepository
import ua.edu.chnu.kkn.florilegium.domain.onboarding.IsOnboardingPassedUseCase
import ua.edu.chnu.kkn.florilegium.domain.onboarding.MarkOnboardingPassedUseCase
import ua.edu.chnu.kkn.florilegium.domain.PreferencesRepository
import ua.edu.chnu.kkn.florilegium.ui.login.LoginViewModel
import ua.edu.chnu.kkn.florilegium.ui.onboarding.OnboardingViewModel

private fun createSettings() : Settings = Settings()

val dataModule = module {
    single { create(::createSettings) } binds arrayOf(Settings::class, ObservableSettings::class)
}

val appModule = module {
    includes(dataModule)
    singleOf(::AppPreferencesRepository) bind PreferencesRepository::class
    factoryOf(::MarkOnboardingPassedUseCase)
    factoryOf(::IsOnboardingPassedUseCase)
    viewModel<AppViewModel>()
    viewModel<OnboardingViewModel>()
    viewModel<LoginViewModel>()
}
