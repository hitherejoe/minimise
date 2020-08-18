package co.joebirch.minimise.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
import javax.inject.Inject

class CreationViewModel @Inject constructor(
) : BaseViewModel() {

    private var uiState =
        MutableLiveData<CreationState>().default(
            CreationState(CreationStep.NAME)
        )

    fun handleCreationEvent(event: CreationEvent) {
        when (event) {
            is CreationEvent.NameChanged -> {
                setProductName(event.name)
            }
            is CreationEvent.CategoriesChanged -> {
                // create categories function
            }
            is CreationEvent.FrequencyChanged -> {
                setFrequency(event.frequency)
            }
            is CreationEvent.ReminderLengthChanged -> {
                setRemindDays(event.days)
            }
            is CreationEvent.PositiveNotesChanged -> {
                setPositives(event.notes)
            }
            is CreationEvent.NegativeNotesChanged -> {
                setNegatives(event.notes)
            }
            CreationEvent.NextStepRequested -> {
                navigateToNextStep()
            }
            CreationEvent.PreviousStepRequested -> {
                navigateToPreviousStep()
            }
            CreationEvent.FormCompleted -> {
                createBelonging()
            }
        }
    }

    fun observeAuthenticationState(): LiveData<CreationState> = uiState

    fun setProductName(name: String) {
        uiState.value =
            uiState.value!!.build {
                this.name = name
            }
    }

    fun setStoreName(name: List<String>) {
        uiState.value =
            uiState.value!!.build {
                this.categories = name
            }
    }

    fun setFrequency(frequency: Float) {
        uiState.value =
            uiState.value!!.build {
                this.frequency = frequency
            }
    }

    fun setRemindDays(daysToRemind: Int) {
        uiState.value =
            uiState.value!!.build {
                this.daysToRemind = daysToRemind
            }
    }

    fun setPositives(positives: List<String>) {
        uiState.value =
            uiState.value!!.build {
                this.positiveReasons = positiveReasons
            }
    }

    fun setNegatives(negatives: List<String>) {
        uiState.value =
            uiState.value!!.build {
                this.negativeReasons = negativeReasons
            }

    }

    fun createBelonging() {
        // send data to api
        uiState.value =
            uiState.value!!.build {
                isLoading = true
            }
    }

    fun navigateToNextStep() {
        uiState.value =
            uiState.value!!.build {
                creationStep = CreationStep.fromPosition(
                    uiState.value?.currentStep?.position!! + 1
                )
            }
    }

    fun currentStep() = uiState.value?.currentStep?.position ?: 0

    fun navigateToPreviousStep() {
        uiState.value =
            uiState.value!!.build {
                creationStep = CreationStep.fromPosition(
                    uiState.value?.currentStep?.position!! - 1
                )
            }
    }
}