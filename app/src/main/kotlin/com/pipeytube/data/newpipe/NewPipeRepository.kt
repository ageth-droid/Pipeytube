package com.pipeytube.data.newpipe

import com.pipeytube.domain.model.CommentsPage
import com.pipeytube.domain.model.StreamVariant
import com.pipeytube.domain.model.VideoDetails
import com.pipeytube.domain.model.VideoListItem
import com.pipeytube.domain.repository.VideoRepository
import java.io.IOException

class NewPipeRepository(
    private val service: NewPipeService
) : VideoRepository {

    override suspend fun searchVideos(query: String): List<VideoListItem> = wrap {
        service.searchVideos(query).map(NewPipeMapper::toDomain)
    }

    override suspend fun getVideoStreams(videoIdOrUrl: String): List<StreamVariant> = wrap {
        service.getVideoStreams(videoIdOrUrl).map(NewPipeMapper::toDomain)
    }

    override suspend fun getVideoDetails(videoIdOrUrl: String): VideoDetails = wrap {
        NewPipeMapper.toDomain(service.getVideoDetails(videoIdOrUrl))
    }

    override suspend fun getComments(videoIdOrUrl: String, pageToken: String?): CommentsPage = wrap {
        NewPipeMapper.toDomain(service.getComments(videoIdOrUrl, pageToken))
    }

    private inline fun <T> wrap(block: () -> T): T = try {
        block()
    } catch (e: IOException) {
        throw NewPipeError.Network(e)
    } catch (e: IllegalArgumentException) {
        throw NewPipeError.Parsing(e)
    } catch (e: IllegalStateException) {
        throw NewPipeError.Extractor(e)
    } catch (e: RuntimeException) {
        throw NewPipeError.Unknown(e)
    }
}
