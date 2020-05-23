package co.joebirch.minimise.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
) : BaseViewModel(), DashboardView {

    private val uiState =
        MutableLiveData<DashboardState>().default(
            DashboardState.Idle()
        )

    fun observeAuthenticationState(): LiveData<DashboardState> = uiState

    fun getBelongings() {
        Firebase.firestore.collection("users").whereEqualTo("", "")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun setSelectedCategory(category: Category) {
        uiState.postValue(
            uiState.value!!.build {
                this.category = category
            }
        )
    }
}