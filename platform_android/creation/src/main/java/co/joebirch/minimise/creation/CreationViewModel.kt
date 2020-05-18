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

    fun setStoreName(name: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.store = name
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

    fun createBelonging() {
        // send data to api
        uiState.postValue(
            uiState.value!!.build {
                isLoading = true
            }
        )
    }

    fun navigateToNextStep() {
        val nextStep = uiState.value?.currentStep?.position!! + 1
        val c = CreationState(CreationStep.fromPosition(nextStep))
        uiState.postValue(c)
    }

    fun navigateToPreviousStep() {
        uiState.postValue(CreationState(CreationStep.fromPosition(
            uiState.value?.currentStep?.position!! - 1)))
    }
}