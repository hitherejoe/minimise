package co.joebirch.minimise.dashboard

import androidx.lifecycle.SavedStateHandle
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.navigation.DashboardDirections
import co.joebirch.minimise.navigation.NavigationManager
import co.joebirch.minimise.shared.dashboard.Belonging
import co.joebirch.minimise.shared.dashboard.Category
import co.joebirch.minimise.shared.dashboard.DashboardState
import co.joebirch.minimise.shared.dashboard.DashboardView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : BaseViewModel(), DashboardView {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState

    fun getBelongings(userId: String) {
        /*
        Firebase.firestore.collection("items")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val documents = mutableListOf<Belonging>()
                for (document in result) {

                }
                _uiState.postValue(
                    _uiState.value!!.build {
                        this.pendingBelongings = documents
                    }
                )
            }
            .addOnFailureListener { exception ->
                val e = ""
            }
         */
        _uiState.value = _uiState.value.build {
            this.pendingBelongings = listOf(
                Belonging(
                    "id",
                    "Name",
                    "Store"
                )
            )
        }
    }

    override fun setSelectedCategory(category: Category) {
        _uiState.value = _uiState.value.build {
            this.category = category
        }
    }

    fun navigateToCreation() {
        navigationManager.navigate(DashboardDirections.creation)
    }
}