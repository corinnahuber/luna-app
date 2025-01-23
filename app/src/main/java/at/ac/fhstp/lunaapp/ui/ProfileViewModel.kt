package at.ac.fhstp.lunaapp.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhstp.lunaapp.data.ProfileRepository
import at.ac.fhstp.lunaapp.data.db.ProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private val _profile = MutableStateFlow<ProfileEntity?>(null)
    val profile: StateFlow<ProfileEntity?> = _profile

    init {
        refreshProfile()
    }

    private fun refreshProfile() {
        viewModelScope.launch {
            _profile.value = profileRepository.getProfile()
        }
    }

    fun updateProfile(context: Context, name: String, age: String, weight: String, contraception: String, imageUri: Uri?) {
        viewModelScope.launch {
            val imagePath = imageUri?.let { uri ->
                val bitmap = loadImageFromUri(context, uri)
                bitmap?.let {
                    saveImageToInternalStorage(context, it, "profile_image.png")
                }
            } ?: _profile.value?.imageUri

            val updatedProfile = ProfileEntity(
                id = _profile.value?.id ?: 0,
                name = name,
                age = age,
                weight = weight,
                contraception = contraception,
                imageUri = imagePath
            )
            if (_profile.value == null) {
                profileRepository.insertProfile(updatedProfile)
            } else {
                profileRepository.updateProfile(updatedProfile)
            }
            refreshProfile()
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            profile.value?.let {
                Log.d("ProfileViewModel", "Deleting profile: $it")
                profileRepository.deleteProfile(it)
                _profile.value = null
                refreshProfile()
            }
        }
    }

    private fun loadImageFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, filename: String): String {
        val file = File(context.filesDir, filename)
        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    fun loadImageFromInternalStorage(context: Context, filename: String): Bitmap? {
        val file = File(context.filesDir, filename)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    companion object {
        fun provideFactory(repository: ProfileRepository) = ProfileViewModelFactory(repository)
    }
}