package com.etnstudio.user.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etnstudio.user.data.models.ItemType
import com.etnstudio.user.data.models.MediaItem
import com.etnstudio.user.domain.repository.ContentRepository
import com.etnstudio.user.domain.repository.RegistryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val registryRepo: RegistryRepository,
    private val contentRepo: ContentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LibraryUiState>(LibraryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = LibraryUiState.Loading
            try {
                val config = registryRepo.fetchRegistry()
                contentRepo.fetchAndCache(config.username, config.repo, config.branch, config.path)
                contentRepo.getFolders().collect { items ->
                    _uiState.value = LibraryUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = LibraryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
