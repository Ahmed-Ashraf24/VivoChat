package com.example.vivochat.presentation.ui.screens.profile_image

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.utility.MediaPickerUtility.uriToFile
import com.example.vivochat.presentation.viewModel.media_viewmodel.MediaState
import com.example.vivochat.presentation.viewModel.media_viewmodel.MediaViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileImageScreen(
    viewModel: MediaViewModel= hiltViewModel(),
    navController: NavController
) {
val context = LocalContext.current
    val state = viewModel.mediaState.collectAsState()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(context, it)
            viewModel.uploadImage(file)
        }
    }

    LaunchedEffect(state.value) {
        if (state.value is MediaState.Success) {
            navController.navigate("login")
        }
    }


    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 30.dp).clickable{
                        viewModel.skipUploadingMessage()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Skip",
                    color = Primary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = Primary,
                    painter = painterResource(R.drawable.outline_arrow_forward_ios_24),
                    contentDescription = "skip"
                )
            }
        }
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(Modifier.padding(bottom = 10.dp)) {
                Surface(
                    modifier = Modifier
                        .size(150.dp)
                        .border(1.dp, Color.Gray.copy(alpha = .4f), RoundedCornerShape(100.dp)),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    AsyncImage(
                        model = viewModel.imageUrl?:"",
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "profile image"
                    )
                }

            }
            if(state.value is MediaState.Loading){
                Box(
                    modifier = Modifier.fillMaxWidth()

                    ,
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Primary)
                }
            }else{
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier
                    .height(57.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                )
            ) {
                    Text("upload photo", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            }
            }


        }

        }
    }
