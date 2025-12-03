package com.example.vivochat.data.dataSource.MediaDatasource

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.vivochat.domain.entity.VideoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CloudinaryDataSource: MediaDataSource {
    private val cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "dblglypwc",
            "api_key", "751973516836692",
            "api_secret", "yX-UsHhY3A5fXklw0Y4qUgusjKw"
        )
    )
    override suspend fun uploadImage(file: File): Result<String> = withContext(Dispatchers.IO) {
        try {
            val result = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "folder", "android_uploads",
                "resource_type", "image"
            ))
            Result.success(result["secure_url"] as String)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadVideo(file: File): Result<VideoResult> = withContext(Dispatchers.IO) {
        try {
            val result = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "folder", "android_videos",
                "resource_type", "video",
                "chunk_size", 6000000
            ))

            val publicId = result["public_id"] as String
            val directUrl = result["secure_url"] as String


            val hlsUrl = cloudinary.url()
                .resourceType("video")
                .format("m3u8")
                .publicId(publicId)
                .generate()

            Result.success(VideoResult(directUrl, hlsUrl, publicId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
