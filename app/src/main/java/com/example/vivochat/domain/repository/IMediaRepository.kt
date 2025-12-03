package com.example.vivochat.domain.repository

import com.example.vivochat.domain.entity.VideoResult
import java.io.File

interface IMediaRepository {
    suspend fun uploadImage(file: File): Result<String>
    suspend fun uploadVideo(file: File): Result<VideoResult>
}