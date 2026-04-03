package com.pipeytube.data.newpipe

import kotlinx.coroutines.delay

/**
 * End-to-end development service. Replace internals with real extractor calls
 * (the NewPipe dependency is already wired at build level).
 */
class FakeExtractorNewPipeService : NewPipeService {
    override suspend fun searchVideos(query: String): List<ExtractorVideoItem> {
        delay(250)
        if (query.isBlank()) return emptyList()
        return (1..12).map {
            ExtractorVideoItem(
                id = "video-$it",
                title = "$query result #$it",
                uploader = "Channel $it",
                durationSeconds = 120L + it,
                thumbnailUrl = "https://picsum.photos/seed/${query.hashCode()}$it/640/360"
            )
        }
    }

    override suspend fun getVideoStreams(videoIdOrUrl: String): List<ExtractorStream> = listOf(
        ExtractorStream("$videoIdOrUrl-360", "mp4", "360p", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
        ExtractorStream("$videoIdOrUrl-720", "mp4", "720p", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
    )

    override suspend fun getVideoDetails(videoIdOrUrl: String): ExtractorVideoDetails {
        delay(100)
        return ExtractorVideoDetails(
            id = videoIdOrUrl,
            title = "Now Playing: $videoIdOrUrl",
            description = "Long description for $videoIdOrUrl. ".repeat(12),
            uploader = "Pipeytube Channel",
            viewCount = 145_678
        )
    }

    override suspend fun getComments(videoIdOrUrl: String, pageToken: String?): ExtractorCommentsPage {
        delay(150)
        val page = pageToken?.toIntOrNull() ?: 0
        val items = (1..10).map {
            val idx = page * 10 + it
            ExtractorComment(
                id = "c-$idx",
                author = "User $idx",
                text = "Comment #$idx on $videoIdOrUrl",
                likeCount = idx.toLong(),
                publishedText = "${idx}m ago"
            )
        }
        val next = if (page < 2) (page + 1).toString() else null
        return ExtractorCommentsPage(items, next)
    }
}
