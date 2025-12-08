package com.example.vivochat.presentation.ui.screens.story

import CircleAvatar
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.vivochat.domain.entity.User
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.utility.TimeFormateUtility
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
data class StoryViewRoute(val user: User)

val UserNavType = object : NavType<User>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): User? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): User {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: User): String {
        return Uri.encode(Json.encodeToString(User.serializer(), value))
    }

    override fun put(bundle: Bundle, key: String, value: User) {
        bundle.putString(key, Json.encodeToString(User.serializer(), value))
    }
}

fun NavGraphBuilder.storyViewScreen() {
    composable<StoryViewRoute>(
        typeMap = mapOf(typeOf<User>() to UserNavType)
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<StoryViewRoute>()
        StoryView(user = route.user)
    }
}

@Composable
fun StoryView(user: User) {
    var index by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    val story = user.stories

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = story?.getOrNull(index)?.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                onState = {
                    isLoading = it is AsyncImagePainter.State.Loading
                }
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Primary
                )
            }
        }
        Column(
            modifier = Modifier.padding(top = 30.dp, start = 10.dp, end = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CircleAvatar(user.imageUrl.toString())
                    Text(user.fullName)
                }

                val formattedDate = user.stories?.getOrNull(index)?.date?.let {
                    TimeFormateUtility.getRelativeTime(it)
                }
                Text(
                    text = formattedDate.orEmpty()
                )
            }
        }


        Row(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxSize()
                    .clickable {
                        if (index > 0) {
                            index -= 1
                        }
                    }
            )


            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxSize()
                    .clickable {
                        if (index + 1 < (user.stories?.size ?: 0)) {
                            index += 1
                        }
                    }
            )
        }
    }
}



