package com.pipeytube.ui.player

import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.pipeytube.domain.repository.VideoRepository

@Composable
fun PlayerScreen(
    videoId: String,
    repository: VideoRepository,
    onBack: () -> Unit
) {
    val vm = remember(videoId, repository) { PlayerViewModel(videoId, repository) }
    val ui by vm.uiState.collectAsState()

    val player = remember { ExoPlayer.Builder(androidx.compose.ui.platform.LocalContext.current).build() }
    val lastPosition = remember { mutableLongStateOf(0L) }

    LaunchedEffect(ui.selectedStream?.url) {
        val url = ui.selectedStream?.url ?: return@LaunchedEffect
        val shouldSeek = lastPosition.longValue > 0
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        if (shouldSeek) player.seekTo(lastPosition.longValue)
        player.playWhenReady = true
    }

    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = onBack) { Text("Back") }
        if (ui.error != null) Text(ui.error ?: "", color = MaterialTheme.colorScheme.error)

        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 640)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            update = {
                it.player = player
                lastPosition.longValue = player.currentPosition
            }
        )

        QualitySelector(
            options = ui.streams.map { it.quality },
            selected = ui.selectedStream?.quality,
            onSelected = { quality ->
                lastPosition.longValue = player.currentPosition
                ui.streams.firstOrNull { it.quality == quality }?.let(vm::onSelectQuality)
            }
        )

        ui.details?.let { details ->
            Text(details.title, style = MaterialTheme.typography.titleMedium)
            val description = if (ui.descriptionExpanded) details.description else details.description.take(180)
            Text(description)
            if (details.description.length > 180) {
                Button(onClick = vm::onToggleDescription) {
                    Text(if (ui.descriptionExpanded) "Collapse" else "Expand")
                }
            }
        }

        Text("Comments", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(ui.comments) { comment ->
                Column {
                    Text(comment.author, style = MaterialTheme.typography.labelMedium)
                    Text(comment.text)
                }
            }
            item {
                if (ui.commentsPageToken != null) {
                    Button(onClick = vm::loadMoreComments) { Text("Load more") }
                }
            }
        }
    }
}

@Composable
private fun QualitySelector(
    options: List<String>,
    selected: String?,
    onSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { expanded.value = true }) { Text("Quality: ${selected ?: "-"}") }
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            options.forEach { quality ->
                DropdownMenuItem(
                    text = { Text(quality) },
                    onClick = {
                        expanded.value = false
                        onSelected(quality)
                    }
                )
            }
        }
    }
}
