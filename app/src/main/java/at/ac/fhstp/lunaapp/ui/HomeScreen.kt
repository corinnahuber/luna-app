package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ac.fhstp.lunaapp.R

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ccl3_logo04),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1f)
        )
    }
}