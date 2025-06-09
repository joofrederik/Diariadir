package id.ac.unpas.tugasbesar.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    initialName: String = "Nama Kamu",
    initialEmail: String = "email@domain.com",
    onBack: () -> Unit = {},
    onSaveProfile: (String, String) -> Unit = { _, _ -> },
    onChangePhoto: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    // Di contoh ini, foto tidak benar-benar berubah, tambahkan logic sesuai kebutuhanmu

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181818))
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(35.dp))
        // Foto profil (avatar)
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable(enabled = isEditing) { if (isEditing) onChangePhoto() },
            contentAlignment = Alignment.Center
        ) {
            // Ganti dengan Image jika ada URI/foto asli
            Text(
                text = name.takeIf { it.isNotBlank() }?.firstOrNull()?.toString() ?: "",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if (isEditing) {
            TextButton(onClick = { onChangePhoto() }) {
                Text("Ubah Foto")
            }
        }
        Spacer(Modifier.height(22.dp))
        // Nama
        if (isEditing) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        } else {
            Text(name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(14.dp))
        // Email
        if (isEditing) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        } else {
            Text(email, color = Color(0xFFBBBBBB), fontSize = 16.sp)
        }
        Spacer(Modifier.height(36.dp))
        // Tombol Edit / Simpan & Batal
        if (isEditing) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        isEditing = false
                        onSaveProfile(name, email)
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Simpan") }
                OutlinedButton(
                    onClick = { isEditing = false },
                    modifier = Modifier.weight(1f)
                ) { Text("Batal") }
            }
        } else {
            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Edit") }
        }
        Spacer(Modifier.weight(1f))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Kembali") }
    }
}