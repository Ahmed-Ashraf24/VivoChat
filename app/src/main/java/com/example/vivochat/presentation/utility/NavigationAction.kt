package com.example.vivochat.presentation.utility

import com.example.vivochat.domain.entity.User

sealed interface NavigationAction {
    data class ChatNavigation(val userName:String,val  userId:String,val  userImageUrl:String ): NavigationAction
    data object LoginNavigation : NavigationAction
    data object RealNavigation : NavigationAction
    data class StoryNavigation(val user: User ): NavigationAction


}