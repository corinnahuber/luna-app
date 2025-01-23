package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhstp.lunaapp.R

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    // Collect the profile state from the ViewModel
    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // Load the custom font
    val customFont = FontFamily(Font(R.font.font01, FontWeight.Normal))
    val customFont2 = FontFamily(Font(R.font.font06, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (screenHeight < 800.dp) 50.dp else 60.dp)
                .background(Color(0xFF534B62), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Profile",
                color = Color.White,
                fontSize = if (screenHeight < 800.dp) 20.sp else 24.sp,
                style = TextStyle(fontFamily = customFont)
            )
        }

        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 30.dp else 40.dp))

        // Profile image container
        Box(
            modifier = Modifier
                .size(if (screenHeight < 800.dp) 120.dp else 150.dp)
                .background(Color(0xFF534B62), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val bitmap = viewModel.loadImageFromInternalStorage(context, "profile_image.png")
            if (profile != null && bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (screenHeight < 800.dp) 120.dp else 150.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (screenHeight < 800.dp) 120.dp else 150.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 40.dp else 60.dp))

        // Display profile details
        Text(
            text = "Name",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = profile?.name ?: "Your Name",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2)
        )
        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 15.dp else 20.dp))
        Text(
            text = "Age",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = profile?.age ?: "Your Age",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2)
        )
        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 15.dp else 20.dp))
        Text(
            text = "Weight",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${profile?.weight ?: "Weight in"} kg",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2)
        )
        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 15.dp else 20.dp))
        Text(
            text = "Contraception Method",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = profile?.contraception ?: "Contraception",
            fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
            style = TextStyle(fontFamily = customFont2)
        )

        Spacer(modifier = Modifier.height(if (screenHeight < 800.dp) 40.dp else 60.dp))

        // Button to navigate to the profile edit screen
        Button(
            onClick = { navController.navigate("profile_edit") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF534B62))
        ) {
            Text(
                text = "Edit Profile",
                color = Color.White,
                fontSize = if (screenHeight < 800.dp) 16.sp else 18.sp,
                style = TextStyle(fontFamily = customFont)
            )
        }
    }
}