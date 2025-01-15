package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import missing.namespace.R

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val moonPhase by homeViewModel.moonPhase.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.ccl3_logo04),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Moon Phase Information
            if (moonPhase != null) {
                Text(
                    text = "Moon Phase: ${moonPhase?.phase}",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Illumination: ${moonPhase?.illumination}%",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Age: ${moonPhase?.age} days",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "Loading moon phase...",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}