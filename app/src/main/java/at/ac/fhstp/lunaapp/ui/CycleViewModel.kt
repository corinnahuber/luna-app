package at.ac.fhstp.lunaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class CycleViewModel(private val repository: CycleRepository) : ViewModel() {
    val allCycles: Flow<List<CycleEntity>> = repository.allCycles.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    val lastCycle: Flow<LocalDate?> = repository.getLastCycleEndDate().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    val averageCycleLength: Flow<Int> = repository.getAverageCycleLength().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        0
    )

    val nextCycleStart: Flow<LocalDate?> = repository.getNextCycleStart().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
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