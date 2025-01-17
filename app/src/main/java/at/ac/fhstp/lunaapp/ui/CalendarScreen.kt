package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhstp.lunaapp.R
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.DayOfWeek

@Composable
fun CalendarScreen(viewModel: CycleViewModel, navController: NavController) {
    val allCycles by viewModel.allCycles.collectAsState(initial = emptyList())

    // Load the custom font
    val customFont = FontFamily(Font(R.font.font01, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF534B62), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Calendar",
                color = Color.White,
                fontSize = 24.sp,
                style = TextStyle(fontFamily = customFont)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                val calendarState = rememberCalendarState()
                Calendar(
                    calendarState = calendarState,
                    modifier = Modifier.wrapContentSize(),
                    firstDayOfWeek = DayOfWeek.MONDAY,
                    dayContent = { day ->
                        val isCurrentMonth = day.date.month == calendarState.monthState.currentMonth.month
                        val cycle = allCycles.find { it.date == day.date.toString() }
                        val backgroundColor = when {
                            cycle?.flowIntensity != null -> Color(0xFFC1B0D9)
                            cycle != null -> Color(0xFFF2EDFF)
                            else -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .border(2.dp, Color.Black, CircleShape)
                                .background(backgroundColor, CircleShape)
                                .clickable {
                                    if (cycle != null) {
                                        navController.navigate("single_cycle_entry/${cycle.id}")
                                    } else {
                                        navController.navigate("add_cycle/${day.date}")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.date.dayOfMonth.toString(),
                                fontSize = 16.sp,
                                color = if (isCurrentMonth) Color.Black else Color.Gray
                            )
                        }
                    }
                )
            }
        }
    }
}