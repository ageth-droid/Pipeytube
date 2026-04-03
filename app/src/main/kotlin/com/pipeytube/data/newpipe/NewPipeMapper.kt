package com.pipeytube.data.newpipe

import com.pipeytube.domain.model.Comment
import com.pipeytube.domain.model.CommentsPage
import com.pipeytube.domain.model.StreamVariant
import com.pipeytube.domain.model.VideoDetails
import com.pipeytube.domain.model.VideoListItem

object NewPipeMapper {
    fun toDomain(item: ExtractorVideoItem): VideoListItem = VideoListItem(
        id = item.id,
        title = item.title,
        channel = item.uploader,
        durationSeconds = item.durationSeconds,
        thumbnailUrl = item.thumbnailUrl
    )

    fun toDomain(stream: ExtractorStream): StreamVariant = StreamVariant(
        id = stream.id,
        format = stream.format,
        quality = stream.quality,
        url = stream.url
    )

    fun toDomain(details: ExtractorVideoDetails): VideoDetails = VideoDetails(
        id = details.id,
        title = details.title,
        description = details.description,
        channel = details.uploader,
        viewCount = details.viewCount
    )

    fun toDomain(comment: ExtractorComment): Comment = Comment(
        id = comment.id,
        author = comment.author,
        text = comment.text,
        likeCount = comment.likeCount,
        publishedText = comment.publishedText
    )

    fun toDomain(page: ExtractorCommentsPage): CommentsPage = CommentsPage(
        items = page.items.map(::toDomain),
        nextPageToken = page.nextPageToken
    )
}
