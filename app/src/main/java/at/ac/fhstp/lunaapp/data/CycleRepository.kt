package at.ac.fhstp.lunaapp.data

import at.ac.fhstp.lunaapp.data.db.CycleDao
import at.ac.fhstp.lunaapp.data.db.CycleEntity
import kotlinx.coroutines.flow.Flow

class CycleRepository(private val cycleDao: CycleDao) {
    val allCycles: Flow<List<CycleEntity>> = cycleDao.getAllCycles()

    suspend fun insert(cycle: CycleEntity): Long {
        return cycleDao.insert(cycle)
    }

    suspend fun update(cycle: CycleEntity) {
        cycleDao.update(cycle)
    }

    suspend fun deleteById(id: Int) {
        cycleDao.deleteById(id)
    }

    fun getCycleById(id: Int): Flow<CycleEntity?> {
        return cycleDao.getCycleById(id)
    }
}