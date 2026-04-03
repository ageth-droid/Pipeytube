package com.pipeytube.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pipeytube.domain.model.Comment
import com.pipeytube.domain.model.StreamVariant
import com.pipeytube.domain.model.VideoDetails
import com.pipeytube.domain.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlayerUiState(
    val loading: Boolean = true,
    val streams: List<StreamVariant> = emptyList(),
    val selectedStream: StreamVariant? = null,
    val details: VideoDetails? = null,
    val comments: List<Comment> = emptyList(),
    val commentsPageToken: String? = null,
    val error: String? = null,
    val descriptionExpanded: Boolean = false
)

class PlayerViewModel(
    private val videoId: String,
    private val repository: VideoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        loadInitial()
    }

    private fun loadInitial() {
        viewModelScope.launch {
            runCatching {
                Triple(
                    repository.getVideoStreams(videoId),
                    repository.getVideoDetails(videoId),
                    repository.getComments(videoId)
                )
            }.onSuccess { (streams, details, commentsPage) ->
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    streams = streams,
                    selectedStream = streams.firstOrNull(),
                    details = details,
                    comments = commentsPage.items,
                    commentsPageToken = commentsPage.nextPageToken
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(loading = false, error = it.message ?: "Load failed")
            }
        }
    }

    fun onSelectQuality(stream: StreamVariant) {
        _uiState.value = _uiState.value.copy(selectedStream = stream)
    }

    fun onToggleDescription() {
        _uiState.value = _uiState.value.copy(descriptionExpanded = !_uiState.value.descriptionExpanded)
    }

    fun loadMoreComments() {
        val token = _uiState.value.commentsPageToken ?: return
        viewModelScope.launch {
            runCatching { repository.getComments(videoId, token) }
                .onSuccess { page ->
                    _uiState.value = _uiState.value.copy(
                        comments = _uiState.value.comments + page.items,
                        commentsPageToken = page.nextPageToken
                    )
                }
        }
    }
}
