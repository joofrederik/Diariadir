package id.ac.unpas.diariadir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            // Ganti bagian ini dengan logic simpan user ke database/REST API/Firebase
            val result = doRegister(username, email, password)
            _registerState.value = when {
                result.isSuccess -> RegisterState.Success
                else -> RegisterState.Error(result.exceptionOrNull()?.message ?: "Registrasi gagal")
            }
        }
    }

    // Simulasi, ganti dengan logic database/REST API/Firebase
    private suspend fun doRegister(username: String, email: String, password: String): Result<Unit> {
        // Contoh: cek email kosong, dsb, bisa tambah validasi
        return if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Semua field harus diisi!"))
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}