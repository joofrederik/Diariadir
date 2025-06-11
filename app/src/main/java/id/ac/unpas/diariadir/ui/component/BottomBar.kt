package id.ac.unpas.diariadir.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import id.ac.unpas.diariadir.ui.theme.BluePrimary

@Composable
fun BottomBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    // Daftar item untuk navigasi bawah
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Favorit" to Icons.Default.Favorite
    )

    NavigationBar(
        containerColor = Color(0xFF181818), // Warna latar belakang bar
        contentColor = Color.White // Warna konten default
    ) {
        items.forEachIndexed { index, item ->
            val (title, icon) = item

            NavigationBarItem(
                // Properti 'selected' menentukan apakah tab ini sedang aktif
                selected = selectedIndex == index,

                // Properti 'onClick' adalah bagian terpenting.
                // Ini harus memanggil onTabSelected dengan indeks yang benar.
                onClick = { onTabSelected(index) },

                // Mengatur ikon untuk tab
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title
                    )
                },

                // Mengatur label teks untuk tab
                label = { Text(title) },

                // Mengatur warna ikon dan teks
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BluePrimary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = BluePrimary,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFF232323) // Warna latar belakang saat item dipilih
                )
            )
        }
    }
}