package com.example.vivochat.domain.entity

sealed class Response {
    data class Failed(val message:String): Response()
    data class Success<T>(val result:T): Response()
    object Loading : Response()

 }