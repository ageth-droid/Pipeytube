package com.pipeytube.data.newpipe

/** Raw extractor-shaped models kept in data layer. */
data class ExtractorVideoItem(
    val id: String,
    val title: String,
    val uploader: String,
    val durationSeconds: Long?,
    val thumbnailUrl: String?
)

data class ExtractorStream(
    val id: String,
    val format: String,
    val quality: String,
    val url: String
)

data class ExtractorVideoDetails(
    val id: String,
    val title: String,
    val description: String,
    val uploader: String,
    val viewCount: Long?
)

data class ExtractorComment(
    val id: String,
    val author: String,
    val text: String,
    val likeCount: Long?,
    val publishedText: String?
)

data class ExtractorCommentsPage(
    val items: List<ExtractorComment>,
    val nextPageToken: String?
)
