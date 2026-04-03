package com.pipeytube.video.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Comment(val author: String, val body: String)

@Composable
fun VideoDetailsRoute() {
    val comments = listOf(
        Comment("DevJane", "Great walkthrough!"),
        Comment("ComposeFan", "Would love a follow-up on architecture."),
        Comment("Media3Nerd", "Quality toggle UX is clean and simple.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Video Details", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Description: This is a placeholder description area for a selected video.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Text("Comments", style = MaterialTheme.typography.titleMedium)
        }

        items(comments) { comment ->
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(comment.author, style = MaterialTheme.typography.labelLarge)
                    Text(comment.body, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
