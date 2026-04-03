package com.pipeytube.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pipeytube.domain.model.VideoListItem
import com.pipeytube.domain.repository.VideoRepository

@Composable
fun SearchScreen(
    repository: VideoRepository,
    onVideoClick: (String) -> Unit
) {
    val vm = remember(repository) { SearchViewModel(repository) }
    val ui by vm.uiState.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = ui.query,
                onValueChange = vm::onQueryChange,
                modifier = Modifier.weight(1f),
                label = { Text("Search") }
            )
            Button(onClick = vm::submit) { Text("Go") }
        }

        when {
            ui.loading -> CircularProgressIndicator()
            ui.error != null -> Text(ui.error ?: "", color = MaterialTheme.colorScheme.error)
            ui.empty -> Text("No videos found")
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ui.results) { item -> VideoRow(item, onVideoClick) }
            }
        }
    }
}

@Composable
private fun VideoRow(item: VideoListItem, onVideoClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onVideoClick(item.id) },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.thumbnailUrl),
            contentDescription = item.title,
            modifier = Modifier.weight(0.35f)
        )
        Column(modifier = Modifier.weight(0.65f)) {
            Text(item.title, style = MaterialTheme.typography.titleSmall)
            Text(item.channel, style = MaterialTheme.typography.bodySmall)
            Text("${item.durationSeconds ?: 0}s", style = MaterialTheme.typography.bodySmall)
        }
    }
}
