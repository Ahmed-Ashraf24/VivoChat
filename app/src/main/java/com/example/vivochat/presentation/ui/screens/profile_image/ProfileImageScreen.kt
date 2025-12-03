package com.example.vivochat.presentation.ui.screens.profile_image

import android.net.Uri
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vivochat.R
import com.example.vivochat.data.dataSource.MediaDatasource.CloudinaryDataSource
import com.example.vivochat.data.repository.CloudinaryRepository
import com.example.vivochat.presentation.ui.theme.Primary
import com.example.vivochat.presentation.utility.MediaPickerUtility.uriToFile
import com.example.vivochat.presentation.viewModel.media_viewmodel.MediaViewModel

@Preview
@Composable
fun ProfileImageScreen(modifier: Modifier = Modifier) {
    val viewModel= MediaViewModel(CloudinaryRepository(CloudinaryDataSource()))
    val context = LocalContext.current
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = uriToFile(context, it)
            viewModel.uploadImage(file)
        }
    }


    Scaffold(
        topBar = {Row(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 30.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
            Text("Skip",color=Primary, fontSize = 18.sp,modifier=Modifier.padding(horizontal = 5.dp))
            Icon(modifier=Modifier.size(18.dp), tint = Primary,painter = painterResource(R.drawable.outline_arrow_forward_ios_24), contentDescription = "skip")
        }}
    ) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(Modifier.padding(bottom = 10.dp)){
            Surface(
                modifier = Modifier
                    .size(150.dp)
                    .border(1.dp, Color.Gray.copy(alpha = .4f), RoundedCornerShape(100.dp)),
                shape = RoundedCornerShape(100.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "profile image"
                )
            }

        }
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
            if (isLoading) {
                CircularProgressIndicator()
                Text("Uploading...")
            }
            else
            Text("upload photo", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }


    }
    }
}