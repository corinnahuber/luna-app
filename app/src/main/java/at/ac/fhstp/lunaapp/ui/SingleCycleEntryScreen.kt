package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.ac.fhstp.lunaapp.R
import at.ac.fhstp.lunaapp.data.CycleRepository

@Composable
fun SingleCycleEntryScreen(cycleRepository: CycleRepository, cycleId: Int) {
    val viewModel: CycleViewModel = viewModel(factory = CycleViewModelFactory(cycleRepository))
    val cycle by viewModel.getCycleById(cycleId).collectAsState(initial = null)

    cycle?.let { cycleData ->
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
                    text = cycleData.date,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // White Box wrapping Symptoms, Temperature, and Flow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Display selected symptoms
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .height(55.dp)
                                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("My Symptoms are...", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .width(300.dp)
                        ) {
                            Column {
                                cycleData.symptoms?.split(", ")?.chunked(3)?.forEach { rowSymptoms ->
                                    Row {
                                        rowSymptoms.forEach { symptom ->
                                            Box(
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .background(Color(0xFFF2EDFF), RoundedCornerShape(20.dp))
                                                    .width(100.dp)
                                                    .height(40.dp)
                                                    .padding(8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = symptom,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(60.dp))

                        // Basal Temperature Display
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .height(55.dp)
                                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("My Basal Temperature is...", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = cycleData.basalTemperature?.toString() ?: "",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(20.dp))
                                    .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
                                    .padding(8.dp)
                                    .width(100.dp)
                                    .height(20.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(text = " °C", fontSize = 20.sp)
                        }

                        Spacer(modifier = Modifier.height(60.dp))

                        // Flow Intensity Display
                        Box(
                            modifier = Modifier
                                .width(300.dp)
                                .height(55.dp)
                                .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("My Flow is...", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            (1..5).forEach { index ->
                                Icon(
                                    painter = painterResource(
                                        id = if (index <= (cycleData.flowIntensity ?: 0)) R.drawable.drop01_filled else R.drawable.drop01_outline
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )
                                if (index < 5) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Edit and Delete Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "Edit",
                    modifier = Modifier.size(30.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}