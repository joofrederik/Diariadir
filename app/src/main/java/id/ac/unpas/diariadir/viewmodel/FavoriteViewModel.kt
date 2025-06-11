package id.ac.unpas.diariadir.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.unpas.diariadir.data.local.entity.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoritUiState(
    val favoriteStories: List<Story> = emptyList()
)

class FavoritViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritUiState())
    val uiState: StateFlow<FavoritUiState> = _uiState.asStateFlow()

    fun isFavorite(story: Story): Boolean {
        return _uiState.value.favoriteStories.any { it.title == story.title && it.author == story.author }
    }

    fun toggleFavorite(story: Story) {
        viewModelScope.launch {
            val currentFavorites = _uiState.value.favoriteStories.toMutableList()
            if (isFavorite(story)) {
                currentFavorites.removeAll { it.title == story.title && it.author == story.author }
            } else {
                currentFavorites.add(story)
            }
            _uiState.update { it.copy(favoriteStories = currentFavorites) }
        }
    }
}