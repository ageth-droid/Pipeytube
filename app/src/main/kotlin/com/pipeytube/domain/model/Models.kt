package com.pipeytube.domain.model

data class VideoListItem(
    val id: String,
    val title: String,
    val channel: String,
    val durationSeconds: Long?,
    val thumbnailUrl: String?
)

data class StreamVariant(
    val id: String,
    val format: String,
    val quality: String,
    val url: String
)

data class VideoDetails(
    val id: String,
    val title: String,
    val description: String,
    val channel: String,
    val viewCount: Long?
)

data class Comment(
    val id: String,
    val author: String,
    val text: String,
    val likeCount: Long?,
    val publishedText: String?
)

data class CommentsPage(
    val items: List<Comment>,
    val nextPageToken: String?
)
