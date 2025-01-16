package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhstp.lunaapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import dev.jamesyox.kastro.luna.LunarEvent
import dev.jamesyox.kastro.luna.LunarPhaseSequence
import dev.jamesyox.kastro.luna.calculateLunarIllumination
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toKotlinLocalDate
import android.util.Log
import java.time.ZoneId
import kotlinx.coroutines.launch
import kotlinx.datetime.daysUntil

@Composable
fun HomeScreen() {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    var moonPhase by remember { mutableStateOf("Loading...") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val date = LocalDate.now()
            moonPhase = getMoonPhaseForDate(date)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Image(
                painter = painterResource(id = R.drawable.ccl3_logo04),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.75f)
            )

            Spacer(modifier = Modifier.height(70.dp))

            val moonPhaseImageRes = getMoonPhaseImageRes(moonPhase)
            if (moonPhaseImageRes != null) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF534B62), shape = RoundedCornerShape(8.dp))
                        .width(380.dp)
                        .height(350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = moonPhaseImageRes),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp) // Increased size
                        )
                        Spacer(modifier = Modifier.height(30.dp)) // Added space below the image
                        Text(
                            text = "$currentDate, $moonPhase",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

fun getMoonPhaseImageRes(phase: String): Int? {
    return when (phase) {
        "New Moon" -> R.drawable.new_moon
        "Waxing Crescent" -> R.drawable.waxing_crescent
        "First Quarter" -> R.drawable.first_quarter
        "Waxing Gibbous" -> R.drawable.waxing_gibbous
        "Full Moon" -> R.drawable.full_moon
        "Waning Gibbous" -> R.drawable.waning_gibbous
        "Last Quarter" -> R.drawable.last_quarter
        "Waning Crescent" -> R.drawable.waning_crescent
        else -> null
    }
}

fun getMoonPhaseForDate(date: LocalDate): String {
    val timeZone = TimeZone.currentSystemDefault()
    val startDate = date.minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant().toKotlinInstant()

    // Only request primary phases
    val primaryPhases = listOf(
        LunarEvent.PhaseEvent.NewMoon,
        LunarEvent.PhaseEvent.FirstQuarter,
        LunarEvent.PhaseEvent.FullMoon,
        LunarEvent.PhaseEvent.LastQuarter
    )

    val lunarPhaseSequence = LunarPhaseSequence(
        start = startDate,
        requestedLunarPhases = primaryPhases
    )

    val targetDate = date.toKotlinLocalDate()
    val events = lunarPhaseSequence.map {
        it.time.toLocalDateTime(timeZone).date to it::class.java.simpleName
    }.toList()
    Log.d("MoonPhase", "Events: $events")

    val previousEvent = events.lastOrNull { it.first <= targetDate }
    val nextEvent = events.firstOrNull { it.first > targetDate }
    Log.d("MoonPhase", "Previous Event: $previousEvent, Next Event: $nextEvent")

    return when {
        previousEvent == null -> "Unknown"
        nextEvent == null -> previousEvent.second ?: "Unknown"
        else -> {
            val daysBetween = previousEvent.first.daysUntil(nextEvent.first)
            val daysFromPrevious = previousEvent.first.daysUntil(targetDate)
            Log.d("MoonPhase", "Days Between: $daysBetween, Days From Previous: $daysFromPrevious")

            val phaseFraction = daysFromPrevious.toDouble() / daysBetween
            val angle = (phaseFraction * 90.0) + when (previousEvent.second) {
                "NewMoon" -> 0.0
                "FirstQuarter" -> 90.0
                "FullMoon" -> 180.0
                "LastQuarter" -> 270.0
                else -> 0.0
            }
            Log.d("MoonPhase", "Phase Fraction: $phaseFraction, Calculated Angle: $angle")

            // Calculate lunar illumination
            val illumination = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toKotlinInstant().calculateLunarIllumination().fraction * 100
            Log.d("MoonPhase", "Illumination: $illumination%")

            // Map angle to the correct phase
            val phaseName = getPhaseFromAngle(angle)
            Log.d("MoonPhase", "Calculated angle: $angle, Moon phase: $phaseName")
            phaseName
        }
    }
}

fun getPhaseFromAngle(angle: Double): String {
    return when {
        angle < 45.0 -> "New Moon"
        angle < 90.0 -> "Waxing Crescent"
        angle == 90.0 -> "First Quarter"
        angle < 135.0 -> "Waxing Gibbous"
        angle == 180.0 -> "Full Moon"
        angle < 225.0 -> "Waning Gibbous"
        angle == 270.0 -> "Last Quarter"
        else -> "Waning Crescent"
    }
}