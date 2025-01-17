package at.ac.fhstp.lunaapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_table LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Update
    suspend fun update(profile: ProfileEntity)

    @Insert
    suspend fun insert(profile: ProfileEntity)

    @Delete
    suspend fun delete(profile: ProfileEntity)
}