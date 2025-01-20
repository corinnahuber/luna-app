package at.ac.fhstp.lunaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.ProfileRepository
import at.ac.fhstp.lunaapp.data.db.CycleDatabase
import at.ac.fhstp.lunaapp.data.db.ProfileDatabase
import at.ac.fhstp.lunaapp.ui.CycleViewModel
import at.ac.fhstp.lunaapp.ui.ProfileViewModel
import at.ac.fhstp.lunaapp.ui.LunaApp

class MainActivity : ComponentActivity() {
    // Initialize CycleViewModel using a factory method
    // This part of code creates an instance of CycleViewModel by first getting the database instance,
    // then creating a repository with the DAO from the database, and finally using the repository to create the ViewModel.
    private val cycleViewModel: CycleViewModel by viewModels {
        val database = CycleDatabase.getDatabase(this)
        val repository = CycleRepository(database.cycleDao())
        CycleViewModel.provideFactory(repository)
    }

    // Initialize ProfileViewModel using a factory method
    // Similar to the CycleViewModel, this part pf code creates an instance of ProfileViewModel by getting the database instance,
    // creating a repository with the DAO from the database, and using the repository to create the ViewModel.
    private val profileViewModel: ProfileViewModel by viewModels {
        val database = ProfileDatabase.getDatabase(this)
        val repository = ProfileRepository(database.profileDao())
        ProfileViewModel.provideFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the LunaApp composable, passing the view models
        // This sets up the UI of the app by calling the LunaApp composable function and passing the initialized view models to it.
        setContent {
            LunaApp(cycleViewModel, profileViewModel)
        }
    }
}