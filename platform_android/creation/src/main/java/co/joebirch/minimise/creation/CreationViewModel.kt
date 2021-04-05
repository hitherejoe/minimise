package co.joebirch.minimise.creation

import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreationViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreationState(CreationStep.NAME))
    val uiState: StateFlow<CreationState> = _uiState

    fun handleCreationEvent(event: CreationEvent) {
        if (event is CreationEvent.FormCompleted) {
            // send data to api
            _uiState.value = uiState.value.build {
                isLoading = true
            }
        } else {
            _uiState.value =
                uiState.value.build {
                    when (event) {
                        is CreationEvent.NameChanged -> this.name = event.name
                        is CreationEvent.CategoriesChanged -> this.categories = event.categories
                        is CreationEvent.FrequencyChanged -> this.frequency = event.frequency
                        is CreationEvent.ReminderLengthChanged -> this.daysToRemind = event.days
                        is CreationEvent.PositiveNotesChanged -> this.positiveReasons = event.notes
                        is CreationEvent.NegativeNotesChanged -> this.negativeReasons = event.notes
                        CreationEvent.NextStepRequested -> {
                            creationStep = CreationStep.fromPosition(
                                uiState.value.currentStep.position + 1
                            )
                        }
                        CreationEvent.PreviousStepRequested -> {
                            creationStep = CreationStep.fromPosition(
                                uiState.value.currentStep.position - 1
                            )
                        }
                    }
                }
        }
    }

    fun currentStep() = uiState.value.currentStep.position

    fun navigateToPreviousStep() {
        _uiState.value =
            uiState.value.build {
                creationStep = CreationStep.fromPosition(
                    uiState.value.currentStep.position - 1
                )
            }
    }
}