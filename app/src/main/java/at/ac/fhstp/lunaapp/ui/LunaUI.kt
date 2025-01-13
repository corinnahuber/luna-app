package at.ac.fhstp.lunaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import at.ac.fhstp.lunaapp.R
import at.ac.fhstp.lunaapp.data.CycleRepository
import at.ac.fhstp.lunaapp.data.db.CycleDatabase
import kotlinx.coroutines.launch

@Composable
fun LunaApp(viewModel: CycleViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val context = LocalContext.current
    val cycleDao = CycleDatabase.getDatabase(context).cycleDao()
    val cycleRepository = CycleRepository(cycleDao)

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentRoute) {
            "home" -> Image(
                painter = painterResource(id = R.drawable.background02),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            "calendar" -> Image(
                painter = painterResource(id = R.drawable.background03),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            "add_cycle" -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2EDFF))
            ) {
                AddCycleScreen(cycleRepository = cycleRepository)
            }
            "insights" -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2EDFF))
            )
            "profile" -> Image(
                painter = painterResource(id = R.drawable.background01),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            "single_cycle_entry/{cycleId}" -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2EDFF))
            ) {
                val cycleId = navBackStackEntry.value?.arguments?.getString("cycleId")?.toInt() ?: 0
                SingleCycleEntryScreen(cycleRepository = cycleRepository, cycleId = cycleId)
            }
        }

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    NavigationBar(
                        containerColor = Color.White,
                        modifier = Modifier
                            .height(80.dp)
                            .width(400.dp)
                            .border(4.dp, Color.Black, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        val contentColor = Color.Black
                        val selectedContentColor = Color.White

                        Spacer(modifier = Modifier.weight(0.05f, true))
                        NavigationBarItem(
                            icon = { Icon(painterResource(id = R.drawable.outline_home_24), contentDescription = "Home", tint = if (currentRoute == "home") selectedContentColor else contentColor) },
                            selected = currentRoute == "home",
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate("home")
                                }
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = selectedContentColor,
                                selectedTextColor = selectedContentColor,
                                indicatorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(0.05f, true))
                        NavigationBarItem(
                            icon = { Icon(painterResource(id = R.drawable.outline_calendar_month_24), contentDescription = "Calendar", tint = if (currentRoute == "calendar") selectedContentColor else contentColor) },
                            selected = currentRoute == "calendar",
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate("calendar")
                                }
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = selectedContentColor,
                                selectedTextColor = selectedContentColor,
                                indicatorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f, true))
                        NavigationBarItem(
                            icon = { Icon(painterResource(id = R.drawable.outline_format_list_bulleted_24), contentDescription = "Insights", tint = if (currentRoute == "insights") selectedContentColor else contentColor) },
                            selected = currentRoute == "insights",
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate("insights")
                                }
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = selectedContentColor,
                                selectedTextColor = selectedContentColor,
                                indicatorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(0.05f, true))
                        NavigationBarItem(
                            icon = { Icon(painterResource(id = R.drawable.outline_person_24), contentDescription = "Profile", tint = if (currentRoute == "profile") selectedContentColor else contentColor) },
                            selected = currentRoute == "profile",
                            onClick = {
                                coroutineScope.launch {
                                    navController.navigate("profile")
                                }
                            },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = selectedContentColor,
                                selectedTextColor = selectedContentColor,
                                indicatorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(0.05f, true))
                    }
                    FloatingActionButton(
                        onClick = { navController.navigate("add_cycle") },
                        shape = CircleShape,
                        modifier = Modifier
                            .size(75.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = -32.dp)
                            .border(4.dp, Color.Black, CircleShape),
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(
                            painterResource(id = R.drawable.drop01_outline),
                            contentDescription = "Add Cycle",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("calendar") { CalendarScreen(viewModel = viewModel, navController = navController) }
                composable("add_cycle") { AddCycleScreen(cycleRepository = cycleRepository) }
                composable("insights") { InsightsScreen() }
                composable("profile") { ProfileScreen() }
                composable("single_cycle_entry/{cycleId}") { backStackEntry ->
                    val cycleId = backStackEntry.arguments?.getString("cycleId")?.toInt() ?: 0
                    SingleCycleEntryScreen(cycleRepository = cycleRepository, cycleId = cycleId)
                }
            }
        }
    }
}