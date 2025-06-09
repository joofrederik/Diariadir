// File: MainActivity.kt
package id.ac.unpas.tugasbesar.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import id.ac.unpas.tugasbesar.viewmodel.RegisterViewModel
import id.ac.unpas.tugasbesar.viewmodel.RegisterState

// Data class untuk Buku
data class Book(val id: Int, val title: String, val author: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookApp {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookApp()
                }
            }
        }
    }
}

class BookApp(fun: () -> Unit) {

}

@Composable
fun BookApp() {
    // Daftar semua buku
    val books = remember {
        listOf(
            Book(1, "Atomic Habits", "James Clear"),
            Book(2, "Deep Work", "Cal Newport"),
        )
    }

    // State untuk daftar favorit (menyimpan ID buku favorit)
    val favoriteBookIds = remember { mutableStateListOf<Int>() }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Daftar Buku", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(16.dp))
        

        Text(text = "Buku Favorit", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        val favoriteBooks = books.filter { favoriteBookIds.contains(it.id) }
        if (favoriteBooks.isEmpty()) {
            Text(text = "Belum ada buku favorit.")
        } else {
            LazyColumn {
                items(favoriteBooks) { book ->
                    FavoriteBookItem(book = book)
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
            .padding(vertical = 8.dp)
            .clickable { onFavoriteToggle(book.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = book.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = "by ${book.author}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                text = if (isFavorite) "★" else "☆",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun FavoriteBookItem(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "by ${book.author}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
