package at.ac.fhstp.lunaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.lunaapp.data.ApiClient
import at.ac.fhstp.lunaapp.data.MoonPhaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _moonPhase = MutableStateFlow<MoonPhaseResponse?>(null)
    val moonPhase: StateFlow<MoonPhaseResponse?> = _moonPhase

    init {
        fetchMoonPhase()
    }

    private fun fetchMoonPhase() {
        viewModelScope.launch {
            try {
                val response = ApiClient.lunarPhaseService.getCurrentMoonPhase("YOUR_API_KEY")
                if (response.isSuccessful) {
                    _moonPhase.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}