package com.example.vivochat.presentation.ui.screens.story

import CircleAvatar
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel


@Composable
fun StoryView(
    sharedViewModel: SharedViewModel
) {
    var index by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    val story = sharedViewModel.selectedUser.value?.stories

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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

                    CircleAvatar(sharedViewModel.selectedUser.value!!.imageUrl.toString())
                    Text(sharedViewModel.selectedUser.value!!.fullName)
                }
                Text(sharedViewModel.getRelativeTime(story?.get(index)!!.date))
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
                        if (index + 1 < sharedViewModel.selectedUser.value!!.stories!!.size) {
                            index += 1
                        }
                    }
            )
        }
    }
}



