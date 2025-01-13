package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.ac.fhstp.lunaapp.R
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddCycleScreen(cycleRepository: CycleRepository) {
    val viewModel: CycleViewModel = viewModel(factory = CycleViewModelFactory(cycleRepository))
    var selectedSymptoms by remember { mutableStateOf(listOf<String>()) }
    var basalTemperature by remember { mutableStateOf("") }
    var flowIntensity by remember { mutableIntStateOf(0) }
    val symptomsList = listOf(
        "Abdominal Cramps", "Acne", "Appetite Changes", "Bladder Incontinence", "Bloating",
        "Breast Pain", "Chills", "Constipation", "Diarrhoea", "Dry Skin", "Fatigue", "Hair Loss",
        "Headache", "Hot Flushes", "Lower Back Pain", "Memory Lapse", "Mood Changes", "Nausea",
        "Night Sweats", "Pelvic Pain", "Sleep Changes", "Vaginal Dryness"
    )

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
                text = "Today's Cycle",
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
                    // Symptoms Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(55.dp)
                            .background(Color(0xFFC1B0D9), RoundedCornerShape(16.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(onClick = { expanded = true }) {
                            Text("My Symptoms are...", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            symptomsList.forEach { symptom ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedSymptoms = if (selectedSymptoms.contains(symptom)) {
                                            selectedSymptoms - symptom
                                        } else {
                                            selectedSymptoms + symptom
                                        }
                                        expanded = false
                                    },
                                    text = { Text(symptom) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display selected symptoms
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                    ) {
                        Column {
                            selectedSymptoms.chunked(3).forEach { rowSymptoms ->
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

                    // Basal Temperature Input
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
                        BasicTextField(
                            value = basalTemperature,
                            onValueChange = {
                                basalTemperature = it.filter { char -> char.isDigit() || char == '.' || char == ',' }
                                    .replace(',', '.')
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = VisualTransformation.None,
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(20.dp))
                                .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
                                .padding(8.dp)
                                .width(100.dp)
                                .height(20.dp)
                        )
                        Text(text = " °C", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(60.dp))

                    // Flow Intensity Selector
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
                            IconButton(
                                onClick = {
                                    flowIntensity = if (flowIntensity == index) 0 else index
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = if (index <= flowIntensity) R.drawable.drop01_filled else R.drawable.drop01_outline
                                    ),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Save Button
        Button(
            onClick = {
                val date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                val cycle = CycleEntity(
                    date = date,
                    symptoms = selectedSymptoms.joinToString(", "),
                    basalTemperature = basalTemperature.toFloatOrNull(),
                    flowIntensity = flowIntensity.takeIf { it > 0 }
                )
                viewModel.insert(cycle) {}
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC1B0D9))
        ) {
            Text("Save", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}