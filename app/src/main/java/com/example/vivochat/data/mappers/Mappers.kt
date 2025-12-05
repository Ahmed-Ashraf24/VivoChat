package com.example.vivochat.data.mappers

import com.example.vivochat.data.dto.UserDto
import com.example.vivochat.domain.entity.User


fun UserDto.toUser(): User {
    return User(
        this.userId,
        this.fullName,
        this.email,
        this.phoneNum,
        this.imageUrl,
        null
    )
}
