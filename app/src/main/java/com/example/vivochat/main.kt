package com.example.vivochat

import android.util.Log
import com.example.vivochat.data.dataSource.FirebaseRemoteDataSource
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val remote= FirebaseRemoteDataSource()

        Log.d("users from firestore",remote.getUsersList().toString())

    }
}