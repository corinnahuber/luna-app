package at.ac.fhstp.lunaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_table")
data class CycleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symptoms: String,
    val basalTemperature: Float,
    val flowIntensity: Int // 1 to 5 hearts
)