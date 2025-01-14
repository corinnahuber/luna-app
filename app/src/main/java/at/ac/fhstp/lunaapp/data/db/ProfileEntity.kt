package at.ac.fhstp.lunaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: String,
    val weight: String,
    val contraception: String,
    val imageUri: String? = null
)