package com.example.vivochat.presentation.utility

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object MediaPickerUtility {
    fun uriToFile(context: Context, uri: Uri): File {

        val inputStream = context.contentResolver.openInputStream(uri)

        val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}")


        inputStream?.use { input ->
            FileOutputStream(file).use { output ->

                input.copyTo(output)
            }
        }
        return file
    }


}