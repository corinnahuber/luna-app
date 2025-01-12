package at.ac.fhstp.lunaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CycleViewModel(private val repository: CycleRepository) : ViewModel() {
    val allCycles: Flow<List<CycleEntity>> = repository.allCycles.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun insert(cycle: CycleEntity, onInserted: (Long) -> Unit) = viewModelScope.launch {
        val id = repository.insert(cycle)
        onInserted(id)
    }

    fun update(cycle: CycleEntity) = viewModelScope.launch {
        repository.update(cycle)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

    fun getCycleById(id: Int): Flow<CycleEntity?> {
        return repository.getCycleById(id)
    }

    companion object {
        fun provideFactory(repository: CycleRepository) = CycleViewModelFactory(repository)
    }
}