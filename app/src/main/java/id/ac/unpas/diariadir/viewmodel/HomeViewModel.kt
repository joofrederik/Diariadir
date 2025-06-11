package id.ac.unpas.diariadir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.unpas.diariadir.ui.screen.BookCardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class HomeState {

    object Loading : HomeState()


    data class Success(
        val recommendedBooks: List<BookCardData>,
        val instantBooks: List<BookCardData>,
        val genres: List<String>
    ) : HomeState()


    data class Error(val message: String) : HomeState()
}


class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeState: StateFlow<HomeState> = _homeState

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            try {
                // This is a simulation. In a real application, you would fetch this data
                // from a database or a remote API.
                val genres = listOf("Romansa", "Fantasi", "Horor")
                val recommendedBooks = listOf(
                    BookCardData("Sayap Surgaku", "Mellyna Dhian", "Romansa", 4.8),
                    BookCardData("Fall Down", "Rekavaandrietta", "Fantasi", 4.2),
                    BookCardData("Bening dan Banyu", "MommieXYZ", "Horor", 3.9),
                    BookCardData("Atlein", "Angelina", "Romansa", 4.5),
                    BookCardData("Another Book", "Author X", "Fantasi", 4.1),
                    BookCardData("Book Example", "Author Y", "Horor", 3.7)
                )
                val instantBooks = listOf(
                    BookCardData("Bombshell", "Firefliesip", "Romansa", 4.6),
                    BookCardData("Play Date", "Reinsabila", "Fantasi", 4.0),
                    BookCardData("H...", "Penulis H...", "Horor", 3.8),
                    BookCardData("Second Chance", "Author B", "Romansa", 4.3),
                    BookCardData("Mystery Night", "Author C", "Fantasi", 4.7)
                )

                _homeState.value = HomeState.Success(
                    recommendedBooks = recommendedBooks,
                    instantBooks = instantBooks,
                    genres = genres
                )
            } catch (e: Exception) {
                _homeState.value = HomeState.Error("Failed to load data: ${e.message}")
            }
        }
    }
}