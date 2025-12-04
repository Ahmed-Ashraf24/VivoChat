package com.example.vivochat.presentation.ui.screens.Story.component

import CircleAvatar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.Poppins
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.utility.MediaPickerUtility.uriToFile
import com.example.vivochat.presentation.viewModel.StoryViewModel.StoryViewModel
import com.example.vivochat.presentation.viewModel.user_view_model.UserViewModel
import com.example.vivochat.presentation.viewModel.shared_view_model.SharedViewModel

@Composable
fun CreateStoryItem(
    modifier: Modifier = Modifier,
    storyViewModel: StoryViewModel,
    viewModel: UserViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavController
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


    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box {
            Surface(shape = RoundedCornerShape(50.dp), modifier = Modifier.clickable {
                //open current user story
                sharedViewModel.sendUser(viewModel.user)
                if (storyViewModel.stories.isNotEmpty()) {
                    navController.navigate("storyViewScreen")
                }
            }) {

                CircleAvatar(viewModel.user.imageUrl)
            }
            Surface(
                modifier = Modifier
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }
                    .size(18.dp)
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(50.dp),
                color = Primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_add_24),
                    "add",
                    Modifier.padding(2.dp)
                )
            }

        }


        Column(
            Modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
        ) {
            Text("Add Story", fontFamily = Poppins)
            Text(
                "Disappears after 24 hours",
                color = Color.Gray.copy(alpha = .5f),
                fontFamily = Poppins
            )
        }
    }
}
