package at.ac.fhstp.lunaapp.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val profile by viewModel.profile.collectAsState()
    val context = LocalContext.current

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
                text = "Profile",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF534B62), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            profile?.imageUri?.let {
                val bitmap = viewModel.loadImageFromInternalStorage(context, "profile_image.png")
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(text = "Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = profile?.name ?: "Name", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Age", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = profile?.age ?: "Age", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Weight", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "${profile?.weight ?: "Weight"} kg", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Contraception Method", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = profile?.contraception ?: "Contraception", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = { navController.navigate("profile_edit") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF534B62))
        ) {
            Text("Edit Profile", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}