package id.ac.unpas.diariadir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            // Ganti bagian ini dengan query ke database/REST API/Firebase
            val success = doLogin(email, password)
            if (success) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Email atau password salah!")
            }
        }
    }

    private suspend fun doLogin(email: String, password: String): Boolean {
        // Contoh hardcode, ganti dengan akses database
        // return userDao.getUser(email, password) != null
        return (email == "test@gmail.com" && password == "password")
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    data class Error(val message: String): LoginState()
}