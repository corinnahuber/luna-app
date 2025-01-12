package at.ac.fhstp.lunaapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {
    @Insert
    suspend fun insert(cycle: CycleEntity): Long

    @Update
    suspend fun update(cycle: CycleEntity)

    @Query("DELETE FROM cycle_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM cycle_table WHERE id = :id")
    fun getCycleById(id: Int): Flow<CycleEntity?>

    @Query("SELECT * FROM cycle_table")
    fun getAllCycles(): Flow<List<CycleEntity>>
}