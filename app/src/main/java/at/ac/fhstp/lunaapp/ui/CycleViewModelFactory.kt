package at.ac.fhstp.lunaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ac.fhstp.lunaapp.data.CycleRepository

class CycleViewModelFactory(private val repository: CycleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CycleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CycleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}