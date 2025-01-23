package at.ac.fhstp.lunaapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.fhstp.lunaapp.R
import dev.jamesyox.kastro.luna.calculateLunarState
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinInstant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    var moonPhase by remember { mutableStateOf("Loading...") }
    val coroutineScope = rememberCoroutineScope()

    // Load the custom font
    val customFont = FontFamily(Font(R.font.font05, FontWeight.Normal))

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val date = LocalDate.now()
            moonPhase = getMoonPhaseForDate(date)
            Log.d("HomeScreen", "Moon phase: $moonPhase")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 35.dp else 70.dp))

            Image(
                painter = painterResource(id = R.drawable.ccl3_logo04),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(if (screenHeight < 800.dp) 0.7f else 0.75f)
            )

            Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 35.dp else 70.dp))

            val moonPhaseImageRes = getMoonPhaseImageRes(moonPhase)
            if (moonPhaseImageRes != null) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF534B62), shape = RoundedCornerShape(8.dp))
                        .width(if (screenHeight < 800.dp) 340.dp else 380.dp)
                        .height(if (screenHeight < 800.dp) 320.dp else 350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Today's Moon Phase",
                            fontSize = if (screenHeight < 800.dp) 18.sp else 20.sp,
                            color = Color.White,
                            style = TextStyle(fontFamily = customFont)
                        )

                        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 30.dp else 30.dp))

                        Image(
                            painter = painterResource(id = moonPhaseImageRes),
                            contentDescription = null,
                            modifier = Modifier.size(if (screenHeight < 800.dp) 150.dp else 200.dp)
                        )

                        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 30.dp else 30.dp))

                        Text(
                            text = "$currentDate, $moonPhase",
                            fontSize = if (screenHeight < 800.dp) 18.sp else 20.sp,
                            color = Color.White,
                            style = TextStyle(fontFamily = customFont)
                        )
                    }
                }
            } else {
                Log.d("HomeScreen", "Moon phase image resource is null for phase: $moonPhase")
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
    val instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toKotlinInstant()
    val latitude = 48.2082
    val longitude = 16.3738
    val lunarState = instant.calculateLunarState(latitude, longitude)
    val phase = lunarState.phase
    return phase::class.java.simpleName
        .replace("LunarPhase\$Intermediate\$", "")
        .replace("LunarPhase\$Primary\$", "")
        .replace("NewMoon", "New Moon")
        .replace("WaxingCrescent", "Waxing Crescent")
        .replace("FirstQuarter", "First Quarter")
        .replace("WaxingGibbous", "Waxing Gibbous")
        .replace("FullMoon", "Full Moon")
        .replace("WaningGibbous", "Waning Gibbous")
        .replace("LastQuarter", "Last Quarter")
        .replace("WaningCrescent", "Waning Crescent")
}