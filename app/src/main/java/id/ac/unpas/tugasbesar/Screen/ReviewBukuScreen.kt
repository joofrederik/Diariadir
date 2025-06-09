package id.ac.unpas.tugasbesar.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.tugasbesar.R
import id.ac.unpas.tugasbesar.model.Story
import androidx.compose.foundation.verticalScroll

// Data class untuk review user
data class UserReview(
    val username: String,
    val rating: Int,
    val comment: String
)

@Composable
fun ReviewBukuScreen(
    story: Story,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isFavorite by remember { mutableStateOf(false) }
    val coverImage = story.imageRes
    val genres = story.tags
    val sinopsis = story.sinopsis
    val altTitle = story.altTitle ?: story.title

    // Dummy awal untuk review (bisa diganti dengan data dari server/database)
    val reviewList = remember {
        mutableStateListOf(
            UserReview("Davina", 5, "Bagus banget bukunya!"),
            UserReview("Budi", 3, "Lumayan, tapi kurang di bagian akhir.")
        )
    }
    val bookRating: Double = if (reviewList.isNotEmpty()) reviewList.map { it.rating }.average() else 0.0
    val bookRatingCount = reviewList.size

    // State untuk rating dan komentar yang diisi user
    var userRating by remember { mutableStateOf(0) }
    var userComment by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181818))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(bottom = 64.dp)
        ) {
            // Tombol kembali
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, start = 6.dp, end = 6.dp),
                contentAlignment = Alignment.TopStart
            ) {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            // --- COVER IMAGE ---
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(coverImage),
                    contentDescription = "Cover",
                    modifier = Modifier
                        .size(180.dp, 250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // --- RATING ---
            Spacer(Modifier.height(18.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = String.format("%.2f", story.rating),
                        color = Color(0xFFEB5757),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            // --- FAVORITE BUTTON ---
            Spacer(Modifier.height(14.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { isFavorite = !isFavorite },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF232323),
                        contentColor = Color.White
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Tambahkan ke Favorit")
                }
            }

            // --- JUDUL ALTERNATIF ---
            Spacer(Modifier.height(18.dp))
            InfoSection(title = "Judul:", value = story.title)
            if (altTitle.isNotEmpty() && altTitle != story.title) {
                InfoSection(title = "Judul Alternatif:", value = altTitle)
            }

            // --- GENRE TAGS ---
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                genres.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF232323), shape = RoundedCornerShape(9.dp))
                            .padding(horizontal = 16.dp, vertical = 7.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text(tag, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // --- SINOPSIS ---
            Spacer(Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .background(Color(0xFF232323), shape = RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = sinopsis,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            // --- FORM: BERIKAN RATING DAN KOMENTAR ---
            Spacer(Modifier.height(22.dp))
            Text(
                "Beri rating dan ulasan",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
            Row(
                Modifier
                    .padding(start = 18.dp, top = 10.dp)
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= userRating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "star",
                        tint = if (i <= userRating) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { userRating = i }
                    )
                }
            }
            OutlinedTextField(
                value = userComment,
                onValueChange = { userComment = it },
                placeholder = { Text("Tulis komentar...") },
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 6.dp)
                    .fillMaxWidth(),
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF292929),
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedContainerColor = Color(0xFF232323),
                    focusedContainerColor = Color(0xFF232323),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Button(
                onClick = {
                    if (userRating > 0 && userComment.isNotBlank()) {
                        reviewList.add(UserReview("Kamu", userRating, userComment))
                        userRating = 0
                        userComment = ""
                    }
                },
                enabled = userRating > 0 && userComment.isNotBlank(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                )
            ) {
                Text("Kirim")
            }

            // --- RATING BESAR DAN JUMLAH ORANG YANG MENGISI KOMENTAR---
            Spacer(Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = String.format("%.2f", story.rating),
                    color = Color(0xFFEB5757),
                    fontWeight = FontWeight.Bold,
                    fontSize = 55.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "star",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(60.dp)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$bookRatingCount",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            // --- ULASAN ORANG LAIN ---
            Text(
                "Ulasan Pengguna",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                reviewList.reversed().forEach { review ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar (inisial user)
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .background(Color.Blue, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                review.username.take(1).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    review.username,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(Modifier.width(8.dp))
                                for (i in 1..5) {
                                    Icon(
                                        imageVector = if (i <= review.rating) Icons.Filled.Star else Icons.Outlined.Star,
                                        contentDescription = "star",
                                        tint = if (i <= review.rating) Color(0xFFFFC107) else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.height(2.dp))
                            Text(
                                review.comment,
                                color = Color(0xFFCCCCCC),
                                fontSize = 14.sp
                            )
                        }
                    }
                    Divider(color = Color(0xFF232323), thickness = 1.dp)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoSection(title: String, value: String, valueColor: Color = Color.White) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 5.dp)
            .background(Color(0xFF232323), shape = RoundedCornerShape(11.dp))
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Color(0xFFBBBBBB),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                color = valueColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}