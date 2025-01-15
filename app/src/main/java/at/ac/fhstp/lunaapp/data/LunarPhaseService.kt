package at.ac.fhstp.lunaapp.data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LunarPhaseService {
    @GET("current")
    suspend fun getCurrentMoonPhase(@Query("apiKey") apiKey: String): Response<MoonPhaseResponse>
}

data class MoonPhaseResponse(
    val phase: String,        // Current moon phase (e.g., "Full Moon")
    val illumination: Double, // Percentage of illumination (e.g., 98.7)
    val age: Double           // Age of the moon in days
)
