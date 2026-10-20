package ua.edu.chnu.kkn.florilegium.ui.onboarding

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ua.edu.chnu.kkn.florilegium.domain.onboarding.MarkOnboardingPassedUseCase

@Stable
internal class OnboardingViewModel(
    private val markOnboardingPassedUseCase: MarkOnboardingPassedUseCase
) : ViewModel() {

    private val _events = Channel<OnboardingEvent>(capacity = Channel.BUFFERED)
    val events: Flow<OnboardingEvent> = _events.receiveAsFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.OnboardingPassed -> {
                markOnboardingPassedUseCase()
                _events.trySend(OnboardingEvent.OnOnboardingPassed)
            }
        }
    }
}
