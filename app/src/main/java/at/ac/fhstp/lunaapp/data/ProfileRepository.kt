package at.ac.fhstp.lunaapp.data

import at.ac.fhstp.lunaapp.data.db.ProfileDao
import at.ac.fhstp.lunaapp.data.db.ProfileEntity

class ProfileRepository(private val profileDao: ProfileDao) {
    suspend fun getProfile(): ProfileEntity? {
        return profileDao.getProfile()
    }

    suspend fun updateProfile(profile: ProfileEntity) {
        profileDao.update(profile)
    }

    suspend fun insertProfile(profile: ProfileEntity) {
        profileDao.insert(profile)
    }

    suspend fun deleteProfile(profile: ProfileEntity) {
        profileDao.delete(profile)
    }
}