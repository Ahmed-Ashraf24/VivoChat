package com.example.vivochat.data.mapper

import com.example.vivochat.data.dto.StoryDto
import com.example.vivochat.domain.entity.Story
import com.example.vivochat.presentation.utility.TimeFormateUtility.getRelativeTime
import kotlin.collections.map

object StoryMapper{
    fun mapDtoListToDomainList(storyDtoList: List<StoryDto>): List<Story> {
        return storyDtoList.map { dto ->
            Story(
                storyId = dto.storyId ?: "",
                imageUrl = dto.imageUrl,
                watchedBy = dto.watchedBy,
                date = getRelativeTime(dto.date)
            )
        }
    }
}