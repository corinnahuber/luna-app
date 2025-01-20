package at.ac.fhstp.lunaapp.data

import at.ac.fhstp.lunaapp.data.db.CycleDao
import at.ac.fhstp.lunaapp.data.db.CycleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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

    fun getLastCycle(): Flow<CycleEntity?> {
        return allCycles.map { cycles ->
            val lastCycle = cycles.maxByOrNull { LocalDate.parse(it.date) }
            lastCycle
        }
    }

    // Retrieves the end date of the last cycle with flow intensity
    fun getLastCycleEndDate(): Flow<LocalDate?> {
        return allCycles.map { cycles ->
            val monthlyCycles = cycles
                .filter { it.flowIntensity != null }
                .groupBy { LocalDate.parse(it.date).withDayOfMonth(1) }
                .map { (_, entries) -> entries.minByOrNull { LocalDate.parse(it.date) } }
                .filterNotNull()
                .sortedBy { LocalDate.parse(it.date) }

            val secondToLastCycle = if (monthlyCycles.size > 1) monthlyCycles[monthlyCycles.size - 2] else null
            secondToLastCycle?.let { LocalDate.parse(it.date) }
        }
    }

    // Calculates the average cycle length in days
    fun getAverageCycleLength(): Flow<Int> {
        return allCycles.map { cycles ->
            val monthlyCycles = cycles
                .filter { it.flowIntensity != null }
                .groupBy { LocalDate.parse(it.date).withDayOfMonth(1) }
                .map { (_, entries) -> entries.minByOrNull { LocalDate.parse(it.date) } }
                .filterNotNull()
                .sortedBy { LocalDate.parse(it.date) }

            // If there are less than 2 cycles, return 0
            if (monthlyCycles.size < 2) {
                return@map 0
            }

            // Calculate the lengths between consecutive cycles
            val lengths = monthlyCycles.zipWithNext { a, b ->
                val length = ChronoUnit.DAYS.between(LocalDate.parse(a.date), LocalDate.parse(b.date)).toInt()
                length
            }

            // Calculate the average length of the cycles
            val averageLength = lengths.filter { it > 0 }.average().toInt()
            averageLength
        }
    }

    // Calculates the start date of the next cycle
    fun getNextCycleStart(): Flow<LocalDate?> {
        return flow {
            val lastCycleDate = getLastCycleEndDate().first()
            val averageLength = getAverageCycleLength().first()
            val nextCycleStart = lastCycleDate?.plusDays(averageLength.toLong())
            emit(nextCycleStart)
        }
    }
}