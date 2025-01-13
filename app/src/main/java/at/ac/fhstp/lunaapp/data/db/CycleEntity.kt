package at.ac.fhstp.lunaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_table")
data class CycleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val symptoms: String? = null,
    val basalTemperature: Float? = null,
    val flowIntensity: Int? = null
)