package id.ac.unpas.diariadir.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import id.ac.unpas.diariadir.ui.theme.BluePrimary // Gunakan import ini

@Composable
fun BottomBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(containerColor = Color(0xFF181818)) {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedIndex == 0) BluePrimary else Color.White
                )
            },
            label = { Text("", color = if (selectedIndex == 0) BluePrimary else Color.White) }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (selectedIndex == 1) BluePrimary else Color.White
                )
            },
            label = { Text("", color = if (selectedIndex == 1) BluePrimary else Color.White) }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorit",
                    tint = if (selectedIndex == 2) BluePrimary else Color.White
                )
            },
            label = { Text("", color = if (selectedIndex == 2) BluePrimary else Color.White) }
        )
    }
}