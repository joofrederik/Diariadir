package id.ac.unpas.diariadir.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.diariadir.ui.component.BottomBar
import id.ac.unpas.diariadir.data.local.entity.Story
import id.ac.unpas.diariadir.R
import id.ac.unpas.diariadir.ui.theme.BluePrimary
import id.ac.unpas.diariadir.viewmodel.HomeState
import id.ac.unpas.diariadir.viewmodel.HomeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel, // Menerima ViewModel
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onGenreClicked: (String) -> Unit,
    onStoryClick: (Story) -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    // Mengumpulkan state dari ViewModel
    val homeState by viewModel.homeState.collectAsState()

    Scaffold(
        containerColor = Color(0xFF181818),
        bottomBar = { BottomBar(selectedIndex = selectedTab, onTabSelected = onTabSelected) }
    ) { padding ->
        // Tampilkan UI berdasarkan state
        when (val state = homeState) {
            is HomeState.Loading -> {
                // Tampilkan loading indicator di tengah layar
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF181818))
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Diariadir",
                        color = BluePrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.4f))
                            .clickable { showProfileMenu = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                        DropdownMenu(
                            expanded = showProfileMenu,
                            onDismissRequest = { showProfileMenu = false },
                            modifier = Modifier
                                .width(170.dp)
                                .background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Profile", color = Color.Black) },
                                onClick = {
                                    showProfileMenu = false
                                    onProfileClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Logout", color = Color.Red) },
                                onClick = {
                                    showProfileMenu = false
                                    onLogoutClick()
                                }
                            )
                        }
                    }
                }
            }
            is HomeState.Success -> {
                // Jika sukses, tampilkan data
                HomeScreenContent(
                    padding = padding,
                    successState = state,
                    onGenreClicked = onGenreClicked,
                    onStoryClick = onStoryClick,
                    onProfileClick = onProfileClick,
                    onLogoutClick = onLogoutClick
                )
            }
            is HomeState.Error -> {
                // Jika error, tampilkan pesan error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues,
    successState: HomeState.Success,
    onGenreClicked: (String) -> Unit,
    onStoryClick: (Story) -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var showProfileMenu by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color(0xFF181818))
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Diariadir",
                    color = BluePrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.4f))
                        .clickable { showProfileMenu = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                    DropdownMenu(
                        expanded = showProfileMenu,
                        onDismissRequest = { showProfileMenu = false },
                        modifier = Modifier
                            .width(170.dp)
                            .background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile", color = Color.Black) },
                            onClick = {
                                showProfileMenu = false
                                onProfileClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout", color = Color.Red) },
                            onClick = {
                                showProfileMenu = false
                                onLogoutClick()
                            }
                        )
                    }
                }
            }
        }
        item { Spacer(Modifier.height(8.dp)) }
        item { SectionTitle("Pilihan terbaik untukmu") }
        item {
            BookCardLazyRow(
                books = successState.recommendedBooks, // Menggunakan data dari state
                onBookClick = { book -> onStoryClick(book.toStory()) }
            )
        }
        item { Spacer(Modifier.height(12.dp)) }
        item { SectionTitle("Direkomendasikan untukmu") }
        item {
            BookCardLazyRow(
                books = successState.instantBooks, // Menggunakan data dari state
                onBookClick = { book -> onStoryClick(book.toStory()) }
            )
        }
        item { Spacer(Modifier.height(12.dp)) }
        item {
            Text(
                text = "Jelajahi genre",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 12.dp)
            )
        }
        item { GenreRow(successState.genres, onGenreClicked) } // Menggunakan data dari state
        item { Spacer(Modifier.height(24.dp)) }
    }
}

// ... (Sisa kode seperti SectionTitle, BookCardData, BookCardLazyRow, BookCard, GenreRow, GenreCard tetap sama)
// ... (Pastikan semua fungsi helper tersebut ada di bawah sini)
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
}

data class BookCardData(
    val title: String,
    val author: String,
    val tag: String,
    val rating: Double = 0.0
)

fun BookCardData.toStory(): Story = Story(
    title = this.title,
    author = this.author,
    imageRes = R.drawable.ic_launcher_foreground, // Placeholder image
    views = "-",
    likes = "-",
    tags = listOf(this.tag),
    genre = this.tag,
    rating = this.rating,
    sinopsis = "Ini adalah sinopsis default. Ganti nanti.",
    altTitle = null
)

@Composable
fun BookCardLazyRow(books: List<BookCardData>, onBookClick: (BookCardData) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(books) { book ->
            BookCard(book, onClick = { onBookClick(book) })
        }
    }
}

@Composable
fun BookCard(book: BookCardData, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(148.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF222222))
            .clickable { onClick() }
            .padding(11.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF1976D2),
                                Color(0xFF0D47A1)
                            )
                        )
                    )
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = book.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.author,
                color = Color(0xFFBBBBBB),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF292929))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = book.tag,
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFFF9800))
                    ),
                    shape = RoundedCornerShape(7.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = String.format(Locale.US, "%.1f/5", book.rating),
                color = Color(0xFF222222),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun GenreRow(genres: List<String>, onGenreClicked: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        genres.forEach {
            GenreCard(it, Modifier.weight(1f), onClick = { onGenreClicked(it) })
        }
    }
}

@Composable
fun GenreCard(
    genre: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF222222))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            lineHeight = 26.sp
        )
    }
}