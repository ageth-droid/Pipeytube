package com.pipeytube.search

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class SearchResult(
    val id: String,
    val title: String,
    val channel: String
)

@Composable
fun SearchRoute() {
    val query = remember { mutableStateOf("") }
    val results = remember {
        listOf(
            SearchResult("1", "Compose Basics", "Pipey Dev"),
            SearchResult("2", "Media3 Quickstart", "Android Streams"),
            SearchResult("3", "Coroutines in Practice", "Kotlin Hub")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Search", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            label = { Text("Search videos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(results.filter { it.title.contains(query.value, ignoreCase = true) || query.value.isBlank() }) { item ->
                SearchResultRow(item)
            }
        }
    }
}

@Composable
private fun SearchResultRow(item: SearchResult) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(width = 110.dp, height = 64.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF2A2D36))
        )

        Column {
            Text(text = item.title, fontWeight = FontWeight.SemiBold)
            Text(text = item.channel, style = MaterialTheme.typography.bodySmall)
        }
    }
}
