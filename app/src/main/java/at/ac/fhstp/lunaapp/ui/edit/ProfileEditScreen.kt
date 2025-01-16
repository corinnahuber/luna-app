package at.ac.fhstp.lunaapp.ui.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhstp.lunaapp.R
import at.ac.fhstp.lunaapp.ui.ProfileViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileEditScreen(navController: NavController, viewModel: ProfileViewModel) {
    val profile by viewModel.profile.collectAsState()
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var age by remember { mutableStateOf(profile?.age ?: "") }
    var weight by remember { mutableStateOf(profile?.weight ?: "") }
    var contraception by remember { mutableStateOf(profile?.contraception ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(profile?.imageUri?.let { Uri.parse(it) }) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

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
                text = "Edit Profile",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF534B62), CircleShape)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Text("Edit", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            } ?: run {
                Text("Edit", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name"
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            value = age,
            onValueChange = { age = it },
            label = "Age",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            value = weight,
            onValueChange = { weight = it.replace(",", ".") },
            label = "Weight",
            keyboardType = KeyboardType.Number,
            suffix = "kg"
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            value = contraception,
            onValueChange = { contraception = it },
            label = "Contraception"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.updateProfile(context, name, age, weight, contraception, imageUri)
                    navController.navigateUp()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF534B62))
            ) {
                Text("Save", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { showDialog = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    modifier = Modifier.size(30.dp),
                    tint = Color(0xFF534B62)
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Delete Profile") },
                text = { Text("Are you sure you want to delete this profile?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteProfile()
                            navController.navigate("profile") {
                                popUpTo("profile_edit_screen") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF534B62))
                    ) {
                        Text("Yes", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF534B62))
                    ) {
                        Text("No", color = Color.White)
                    }
                }
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    suffix: String? = null
) {
    Column {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(200.dp)
                .background(Color(0x80FFFFFF), RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp)
            )
            suffix?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )
            }
        }
    }
}