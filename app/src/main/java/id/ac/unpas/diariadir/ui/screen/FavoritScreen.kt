package id.ac.unpas.diariadir.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.ac.unpas.diariadir.data.local.entity.Story
import id.ac.unpas.diariadir.ui.component.BottomBar
import id.ac.unpas.diariadir.ui.theme.BluePrimary

@Composable
fun FavoritScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritViewModel, // Menerima ViewModel dari luar
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onStoryClick: (Story) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        containerColor = Color(0xFF181818),
        bottomBar = {
            BottomBar(selectedIndex = selectedTab, onTabSelected = onTabSelected)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF181818))
        ) {
            Text(
                text = "Buku Favorit",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            )

            if (uiState.favoriteStories.isEmpty()) {
                Text(
                    text = "Belum ada buku favorit.",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.favoriteStories) { story ->
                        FavoriteBookItem(story = story, onClick = { onStoryClick(story) })
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteBookItem(story: Story, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { onClick() }, // Card bisa diklik
        colors = CardDefaults.cardColors(containerColor = BluePrimary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = story.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "by ${story.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}