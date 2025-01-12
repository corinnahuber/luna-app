package at.ac.fhstp.lunaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleDatabase
import at.ac.fhstp.lunaapp.ui.CycleViewModel
import at.ac.fhstp.lunaapp.ui.LunaApp

class MainActivity : ComponentActivity() {
    private val viewModel: CycleViewModel by viewModels {
        val database = CycleDatabase.getDatabase(this)
        val repository = CycleRepository(database.cycleDao())
        CycleViewModel.provideFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunaApp(viewModel)
        }
    }
}