package com.example.vivochat.presentation.view.home.components

import CircleAvatar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.screens.story.StoryViewRoute
import com.example.vivochat.presentation.utility.MediaPickerUtility.uriToFile
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel

@Composable
fun AddStoryAvatar(
    viewModel: UserViewModel,
    storyViewModel: StoryViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(context, it)
            storyViewModel.uploadStory(file, viewModel.user)
        }
    }



    Column(
        modifier = Modifier.padding(start = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.clickable {
            sharedViewModel.sendUser(viewModel.user)
            if (storyViewModel.stories.isNotEmpty()) {
                navController.navigate(route = StoryViewRoute(viewModel.user))
            }
        }) {
            CircleAvatar(viewModel.user.imageUrl)
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (15).dp, y = (15).dp),
                onClick = {
                    imagePickerLauncher.launch("image/*")
                }) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

        }
        Spacer(Modifier.height(4.dp))
        Text(
            "Your Story",
            maxLines = 1,
            fontSize = 10.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}