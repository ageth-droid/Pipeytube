package com.pipeytube.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val qualityOptions = listOf("Auto", "1080p", "720p", "480p")

@Composable
fun PlayerRoute() {
    var selectedQuality by remember { mutableStateOf("Auto") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Player", style = MaterialTheme.typography.headlineSmall)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Black)
        )

        Text("Current quality: $selectedQuality", style = MaterialTheme.typography.bodyMedium)
        Text("Select quality", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            qualityOptions.forEach { option ->
                AssistChip(
                    onClick = { selectedQuality = option },
                    label = { Text(option) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (option == selectedQuality) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                )
            }
        }

        Text(
            "Playback placeholder: integrate ExoPlayer/Media3 in this surface next.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
