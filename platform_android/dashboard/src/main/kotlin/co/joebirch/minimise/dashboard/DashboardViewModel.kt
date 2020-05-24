package co.joebirch.minimise.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
) : BaseViewModel(), DashboardView {

    private val _uiState =
        MutableLiveData<DashboardState>().default(
            DashboardState()
        )
    val uiState: LiveData<DashboardState> = _uiState

    fun getBelongings(userId: String) {
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
    }

    override fun setSelectedCategory(category: Category) {
        _uiState.postValue(
            _uiState.value!!.build {
                this.category = category
            }
        )
    }
}