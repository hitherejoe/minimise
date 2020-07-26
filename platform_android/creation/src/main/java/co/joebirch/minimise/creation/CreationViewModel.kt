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

    fun observeAuthenticationState(): LiveData<CreationState> = uiState

    fun setProductName(name: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.name = name
            }
        )
    }

    fun setStoreName(name: List<String>) {
        uiState.postValue(
            uiState.value!!.build {
                this.categories = name
            }
        )
    }

    fun setFrequency(frequency: Float) {
        uiState.postValue(
            uiState.value!!.build {
                this.frequency = frequency
            }
        )
    }

    fun setRemindDays(daysToRemind: Int) {
        uiState.postValue(
            uiState.value!!.build {
                this.daysToRemind = daysToRemind
            }
        )
    }

    fun setPositives(positives: List<String>) {
        uiState.postValue(
            uiState.value!!.build {
                this.positiveReasons = positiveReasons
            }
        )
    }

    fun setNegatives(negatives: List<String>) {
        uiState.postValue(
            uiState.value!!.build {
                this.negativeReasons = negativeReasons
            }
        )
    }

    fun createBelonging() {
        // send data to api
        uiState.postValue(
            uiState.value!!.build {
                isLoading = true
            }
        )
    }

    fun navigateToNextStep() {
        uiState.postValue(
            uiState.value!!.build {
                creationStep = CreationStep.fromPosition(
                    uiState.value?.currentStep?.position!! + 1)
            }
        )
    }

    fun navigateToPreviousStep() {
        uiState.postValue(
            uiState.value!!.build {
                creationStep = CreationStep.fromPosition(
                    uiState.value?.currentStep?.position!! - 1)
            }
        )
    }
}