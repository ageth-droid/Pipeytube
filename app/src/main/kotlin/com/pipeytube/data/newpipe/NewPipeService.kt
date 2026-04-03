package com.pipeytube.data.newpipe

interface NewPipeService {
    suspend fun searchVideos(query: String): List<ExtractorVideoItem>
    suspend fun getVideoStreams(videoIdOrUrl: String): List<ExtractorStream>
    suspend fun getVideoDetails(videoIdOrUrl: String): ExtractorVideoDetails
    suspend fun getComments(videoIdOrUrl: String, pageToken: String? = null): ExtractorCommentsPage
}
