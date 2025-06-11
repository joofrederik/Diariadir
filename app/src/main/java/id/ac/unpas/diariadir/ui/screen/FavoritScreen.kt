package id.ac.unpas.diariadir.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.ac.unpas.diariadir.ui.component.BottomBar
import id.ac.unpas.diariadir.ui.theme.BluePrimary

data class Book(val id: Int, val title: String, val author: String)

@Composable
fun FavoritScreen(
    selectedTab: Int = 2,
    onTabSelected: (Int) -> Unit = {}
) {
    val books = remember {
        listOf(
            Book(1, "Atomic Habits", "James Clear"),
            Book(2, "Deep Work", "Cal Newport"),
        )
    }

    val favoriteBookIds = remember { mutableStateListOf<Int>() }

    Scaffold(
        containerColor = Color(0xFF181818),
        bottomBar = {
            BottomBar(selectedIndex = selectedTab, onTabSelected = onTabSelected)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF181818))
        ) {
            Text(
                text = "Daftar Buku",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            )

            LazyColumn {
                items(books) { book ->
                    BookItem(
                        book = book,
                        isFavorite = favoriteBookIds.contains(book.id),
                        onFavoriteToggle = { id ->
                            if (favoriteBookIds.contains(id)) {
                                favoriteBookIds.remove(id)
                            } else {
                                favoriteBookIds.add(id)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Buku Favorit",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            val favoriteBooks = books.filter { favoriteBookIds.contains(it.id) }
            if (favoriteBooks.isEmpty()) {
                Text(
                    text = "Belum ada buku favorit.",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                LazyColumn {
                    items(favoriteBooks) { book ->
                        FavoriteBookItem(book = book)
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, isFavorite: Boolean, onFavoriteToggle: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onFavoriteToggle(book.id) },
        colors = CardDefaults.cardColors(containerColor = BluePrimary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    text = "by ${book.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Text(
                text = if (isFavorite) "★" else "☆",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun FavoriteBookItem(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = BluePrimary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}