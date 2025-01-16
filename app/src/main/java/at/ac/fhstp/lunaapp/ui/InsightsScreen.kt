package at.ac.fhstp.lunaapp.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun InsightsScreen(context: Context, viewModel: CycleViewModel = viewModel(factory = CycleViewModelFactory(CycleRepository(CycleDatabase.getDatabase(context).cycleDao())))) {
    val lastCycleEndDate by viewModel.lastCycle.collectAsState(initial = null)
    val averageCycleLength by viewModel.averageCycleLength.collectAsState(initial = 0)
    val nextCycleStart by viewModel.nextCycleStart.collectAsState(initial = null)

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                text = "Insights",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Last Period Section
        Box(
            modifier = Modifier
                .width(300.dp)
                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Last Cycle",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Start: ${lastCycleEndDate?.format(dateFormatter) ?: "Sorry, we don't have enough data yet"}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "End: ${lastCycleEndDate?.plusDays(averageCycleLength.toLong())?.format(dateFormatter) ?: "Sorry, we don't have enough data yet"}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Next Period Section
        Box(
            modifier = Modifier
                .width(300.dp)
                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Next Cycle",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Start: ${nextCycleStart?.format(dateFormatter) ?: "Sorry, we don't have enough data yet"}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "End: ${nextCycleStart?.plusDays(averageCycleLength.toLong())?.format(dateFormatter) ?: "Sorry, we don't have enough data yet"}",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Average Cycle Length Section
        Box(
            modifier = Modifier
                .width(300.dp)
                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Average Cycle Length",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = if (averageCycleLength > 0) "$averageCycleLength days" else "Sorry, we don't have enough data yet",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
        }
    }
}