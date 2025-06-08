package id.ac.unpas.tugasbesar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.ac.unpas.tugasbesar.ui.theme.TugasBesarTheme
import id.ac.unpas.tugasbesar.Screen.HomeScreen
import id.ac.unpas.tugasbesar.Screen.LoginScreen
import id.ac.unpas.tugasbesar.Screen.RegisterScreen
import id.ac.unpas.tugasbesar.Screen.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasBesarTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    // State untuk tab yang aktif
    var selectedTab by remember { mutableStateOf(0) }

    // Sinkronkan selectedTab dengan route
    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collect { entry ->
            selectedTab = when (entry.destination.route) {
                "home" -> 0
                "search/{genre}" -> 1
                "search" -> 1
                else -> 0
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    when (tabIndex) {
                        0 -> if (navController.currentDestination?.route != "home") navController.navigate("home")
                        1 -> if (navController.currentDestination?.route != "search") navController.navigate("search")
                    }
                },
                onGenreClicked = { genre ->
                    navController.navigate("search/$genre")
                }
            )
        }
        composable("search") {
            SearchScreen(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    when (tabIndex) {
                        0 -> if (navController.currentDestination?.route != "home") navController.navigate("home")
                        1 -> if (navController.currentDestination?.route != "search") navController.navigate("search")
                    }
                },
                initialGenre = "Romansa" // default genre jika akses dari tab
            )
        }
        composable("search/{genre}") { backStackEntry ->
            val genre = backStackEntry.arguments?.getString("genre") ?: "Romansa"
            SearchScreen(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    when (tabIndex) {
                        0 -> if (navController.currentDestination?.route != "home") navController.navigate("home")
                        1 -> if (navController.currentDestination?.route != "search/$genre") navController.navigate("search/$genre")
                    }
                },
                initialGenre = genre
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TugasBesarTheme {
        MyApp()
    }
}