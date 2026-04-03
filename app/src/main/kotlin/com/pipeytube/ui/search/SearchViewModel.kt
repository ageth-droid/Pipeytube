package com.pipeytube.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipeytube.domain.model.VideoListItem
import com.pipeytube.domain.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val loading: Boolean = false,
    val results: List<VideoListItem> = emptyList(),
    val error: String? = null,
    val empty: Boolean = false
)

class SearchViewModel(
    private val repository: VideoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(value: String) {
        _uiState.value = _uiState.value.copy(query = value)
    }

    fun submit() {
        val query = _uiState.value.query
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null, empty = false)
            runCatching { repository.searchVideos(query) }
                .onSuccess { items ->
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        results = items,
                        empty = items.isEmpty()
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(loading = false, error = it.message ?: "Search failed")
                }
        }
    }
}
