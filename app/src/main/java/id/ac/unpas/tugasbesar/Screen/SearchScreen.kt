package id.ac.unpas.tugasbesar.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.tugasbesar.Component.BottomBar
import id.ac.unpas.tugasbesar.R
import id.ac.unpas.tugasbesar.ui.theme.BluePrimary
import id.ac.unpas.tugasbesar.ui.theme.BlueSecondary
import id.ac.unpas.tugasbesar.ui.theme.BlueLight
import id.ac.unpas.tugasbesar.ui.theme.BlueVeryLight

data class Story(
    val title: String,
    val author: String,
    val imageRes: Int,
    val views: String,
    val likes: String,
    val tags: List<String>,
    val genre: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    initialGenre: String = "Romansa",
    onGenreSelected: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedGenre by remember { mutableStateOf(initialGenre) }
    val genres = listOf("Romansa", "Fantasi", "Horor")
    val allStories = listOf(
        Story(
            "Kembalinya Nona Muda (End)", "seeyoulea",
            R.drawable.ic_launcher_foreground,
            "2,28 Jt", "115", listOf("aksi", "fantasi", "romansa"), "Romansa"
        ),
        Story(
            "Softer than Summer Night", "scrluv",
            R.drawable.ic_launcher_foreground,
            "3,23 Jt", "44", listOf("adult", "cinta", "cintasama"), "Romansa"
        ),
        Story(
            "Meraih Cinta Suamiku", "Mamaalva",
            R.drawable.ic_launcher_foreground,
            "932 Rb", "38", listOf("abdi", "airmata", "anggota"), "Romansa"
        ),
        Story(
            "Love Under The Galaxy (TAMAT)", "Zeanisa_",
            R.drawable.ic_launcher_foreground,
            "14,5 Rb", "26", listOf("anfight", "chicklit", "comedi"), "Fantasi"
        ),
        Story(
            "Wild Cousin (21+)", "voltasée",
            R.drawable.ic_launcher_foreground,
            "2,57 Jt", "16", listOf("dewasa", "romansa"), "Horor"
        )
    )

    // Jika searchQuery kosong → filter genre saja.
    // Jika searchQuery terisi, cari ke semua genre!
    val filteredStories = remember(searchQuery.text, selectedGenre) {
        if (searchQuery.text.isEmpty()) {
            allStories.filter { it.genre.equals(selectedGenre, ignoreCase = true) }
        } else {
            allStories.filter {
                it.title.contains(searchQuery.text, ignoreCase = true) ||
                        it.author.contains(searchQuery.text, ignoreCase = true)
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF181818),
        bottomBar = { BottomBar(selectedIndex = selectedTab, onTabSelected = onTabSelected) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF181818))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Telusuri cerita") },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Cari"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF292929),
                    focusedBorderColor = BluePrimary, // Ganti dari oren ke biru
                    unfocusedContainerColor = Color(0xFF292929),
                    focusedContainerColor = Color(0xFF292929),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                genres.forEach { genre ->
                    val isSelected = selectedGenre == genre
                    Text(
                        text = genre,
                        color = if (isSelected) BluePrimary else Color.White, // Ganti dari oren ke biru
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                            .clickable {
                                selectedGenre = genre
                                onGenreSelected(genre)
                            }
                    )
                }
            }
            Text(
                text = "${filteredStories.size} Cerita",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(filteredStories) { story ->
                    StorySearchItem(story)
                    HorizontalDivider(color = Color(0xFF222222), thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun StorySearchItem(story: Story) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(story.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(62.dp, 90.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                story.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                story.author,
                color = Color.Gray,
                fontSize = 13.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "views",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    story.views,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "likes",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    story.likes,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(modifier = Modifier.padding(top = 4.dp)) {
                story.tags.take(3).forEach {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF292929), shape = MaterialTheme.shapes.small)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            it,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
                if (story.tags.size > 3) {
                    Text(
                        "+ selanjutnya",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}