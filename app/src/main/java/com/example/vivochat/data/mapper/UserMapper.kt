package com.example.vivochat.data.mapper

import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.entity.User

object UserMapper {

    fun toUser(user: UserDto): User {
    return User(
        userId = user.userId,
        fullName = user.fullName,
        email = user.email,
        phoneNum = user.phoneNum,
        imageUrl = user.imageUrl
    )

    }
}