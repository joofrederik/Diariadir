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
import id.ac.unpas.diariadir.viewmodel.LoginViewModel
import id.ac.unpas.diariadir.viewmodel.LoginState

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by loginViewModel.loginState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181818)) // background jadi hitam
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("diariadir", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            Spacer(Modifier.height(24.dp))
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
                onClick = { loginViewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            when (loginState) {
                is LoginState.Error -> {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        (loginState as LoginState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is LoginState.Success -> {
                    LaunchedEffect(Unit) { onLoginSuccess() }
                }
                else -> {}
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onRegisterClick) {
                Text("Belum punya akun? Registrasi")
            }
        }
    }
}