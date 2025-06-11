package id.ac.unpas.diariadir

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import id.ac.unpas.diariadir.data.local.entity.Story
import id.ac.unpas.diariadir.ui.screen.*
import id.ac.unpas.diariadir.ui.theme.TugasBesarTheme
import id.ac.unpas.diariadir.viewmodel.HomeViewModel

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
    // NavController untuk mengatur semua navigasi di aplikasi
    val navController = rememberNavController()

    // State untuk menyimpan data sederhana yang relevan di seluruh aplikasi
    var selectedTab by remember { mutableIntStateOf(0) }
    var currentUserName by remember { mutableStateOf("Nama Kamu") }
    var currentUserEmail by remember { mutableStateOf("email@domain.com") }

    // Inisialisasi ViewModel yang akan dibagikan ke beberapa layar
    val favoritViewModel: FavoritViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    // Efek untuk menyinkronkan tampilan tab navigasi bawah dengan rute saat ini
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentRoute = backStackEntry.destination.route?.split("/")?.first()
            selectedTab = when (currentRoute) {
                "home" -> 0
                "search" -> 1
                "favorit" -> 2
                else -> selectedTab
            }
        }
    }

    // Logika terpusat untuk menangani klik pada navigasi bawah (BottomBar)
    val onTabSelected: (Int) -> Unit = { tabIndex ->
        val route = when (tabIndex) {
            0 -> "home"
            1 -> "search/Romansa" // Genre default saat tab pencarian diklik
            2 -> "favorit"
            else -> "home"
        }

        navController.navigate(route) {
            // Menghindari penumpukan riwayat navigasi yang tidak perlu
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            // Mencegah layar yang sama dibuka berulang kali
            launchSingleTop = true
            // Memulihkan state (seperti posisi scroll) saat kembali ke layar
            restoreState = true
        }
    }

    // NavHost adalah container utama yang menampung semua layar (composable)
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                }
            )
        }

        composable("register") {
            RegisterScreen(onBackToLogin = { navController.popBackStack() })
        }

        composable("profile") {
            ProfileScreen(
                initialName = currentUserName,
                initialEmail = currentUserEmail,
                onBack = { navController.popBackStack() },
                onSaveProfile = { name, email ->
                    currentUserName = name
                    currentUserEmail = email
                }
            )
        }

        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                onGenreClicked = { genre -> navController.navigate("search/$genre") },
                onStoryClick = { story ->
                    val storyJson = Uri.encode(Gson().toJson(story))
                    navController.navigate("reviewbuku/$storyJson")
                },
                onProfileClick = { navController.navigate("profile") },
                onLogoutClick = { navController.navigate("login") }
            )
        }

        composable("favorit") {
            FavoritScreen(
                viewModel = favoritViewModel, // Memberikan ViewModel yang sama
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                onStoryClick = { story ->
                    val storyJson = Uri.encode(Gson().toJson(story))
                    navController.navigate("reviewbuku/$storyJson")
                }
            )
        }

        composable("search/{genre}") { backStackEntry ->
            val genre = backStackEntry.arguments?.getString("genre") ?: "Romansa"
            SearchScreen(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                initialGenre = genre,
                onStoryClick = { story ->
                    val storyJson = Uri.encode(Gson().toJson(story))
                    navController.navigate("reviewbuku/$storyJson")
                }
            )
        }

        composable("reviewbuku/{story}") { backStackEntry ->
            val storyJson = backStackEntry.arguments?.getString("story") ?: ""
            val story = Gson().fromJson(storyJson, Story::class.java)

            // Mengambil status favorit dari ViewModel agar selalu update
            val favoritState by favoritViewModel.uiState.collectAsState()
            val isFavorite = favoritState.favoriteStories.any { it.title == story.title && it.author == story.author }

            ReviewBukuScreen(
                story = story,
                isFavorite = isFavorite,
                onFavoriteClick = { favoritViewModel.toggleFavorite(story) },
                onBack = { navController.popBackStack() }
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