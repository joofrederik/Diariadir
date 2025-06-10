package id.ac.unpas.diariadir.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import id.ac.unpas.diariadir.viewmodel.RegisterViewModel
import id.ac.unpas.diariadir.viewmodel.RegisterState

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    registerViewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val registerState by registerViewModel.registerState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181818)) // background hitam
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Registrasi", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF222222),
                    focusedContainerColor = Color(0xFF222222),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedBorderColor = Color(0xFF444444),
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF222222),
                    focusedContainerColor = Color(0xFF222222),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedBorderColor = Color(0xFF444444),
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF222222),
                    focusedContainerColor = Color(0xFF222222),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedBorderColor = Color(0xFF444444),
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { registerViewModel.register(username, email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrasi")
            }
            when (registerState) {
                is RegisterState.Error -> {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        (registerState as RegisterState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is RegisterState.Success -> {
                    // Setelah sukses, bisa langsung balik ke login atau tampilkan pesan sukses
                    LaunchedEffect(Unit) { onBackToLogin() }
                }
                else -> {}
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBackToLogin) {
                Text("Sudah punya akun? Login")
            }
        }
    }
}