package com.pipeytube.domain.repository

import com.pipeytube.domain.model.CommentsPage
import com.pipeytube.domain.model.StreamVariant
import com.pipeytube.domain.model.VideoDetails
import com.pipeytube.domain.model.VideoListItem

interface VideoRepository {
    suspend fun searchVideos(query: String): List<VideoListItem>
    suspend fun getVideoStreams(videoIdOrUrl: String): List<StreamVariant>
    suspend fun getVideoDetails(videoIdOrUrl: String): VideoDetails
    suspend fun getComments(videoIdOrUrl: String, pageToken: String? = null): CommentsPage
}
