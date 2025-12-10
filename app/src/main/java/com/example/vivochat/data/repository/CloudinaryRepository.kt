package com.example.vivochat.data.repository

import com.example.vivochat.data.dataSource.MediaDatasource.MediaDataSource
import com.example.vivochat.domain.entity.VideoResult
import com.example.vivochat.domain.repository.IMediaRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudinaryRepository @Inject constructor(private val mediaDataSource: MediaDataSource): IMediaRepository {
    override suspend fun uploadImage(file: File): Result<String> {
        return mediaDataSource.uploadImage(file)
    }

    override suspend fun uploadVideo(file: File): Result<VideoResult> {
        return mediaDataSource.uploadVideo(file)
    }
}