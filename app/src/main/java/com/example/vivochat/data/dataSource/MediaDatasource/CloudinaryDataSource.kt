package com.example.vivochat.data.dataSource.MediaDatasource

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.vivochat.domain.entity.VideoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CloudinaryDataSource @Inject constructor(): MediaDataSource {
    private val cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "name ",
            "api_key", "api key",
            "api_secret", "secret key"
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

            Result.success(VideoResult(directUrl, hlsUrl))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
