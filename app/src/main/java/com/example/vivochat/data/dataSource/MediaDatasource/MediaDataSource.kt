package com.example.vivochat.data.dataSource.MediaDatasource

import com.example.vivochat.domain.entity.VideoResult
import java.io.File

interface MediaDataSource {
    suspend fun uploadImage(file: File): Result<String>
    suspend fun uploadVideo(file: File): Result<VideoResult>
}