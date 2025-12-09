package com.example.vivochat.presentation.utility

import android.os.Bundle
import androidx.navigation.NavType
import com.example.vivochat.domain.entity.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

object UserNavType : NavType<User>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: User) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun get(bundle: Bundle, key: String): User {
        return Json.decodeFromString(bundle.getString(key)!!)
    }

    override fun parseValue(value: String): User {
        return Json.decodeFromString(URLDecoder.decode(value, "UTF-8"))
    }

    override fun serializeAsValue(value: User): String {
        return URLEncoder.encode(Json.encodeToString(value), "UTF-8")
    }
}