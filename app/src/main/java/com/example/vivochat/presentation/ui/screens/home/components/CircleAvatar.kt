import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vivochat.R
import com.example.vivochat.presentation.viewModel.home_view_model.HomeState
import com.example.vivochat.presentation.viewModel.home_view_model.HomeViewModel

@Composable
fun CircleAvatar(
    imageUrl: String?
) {

   if(imageUrl==null){
       Image(
           painter = painterResource(R.drawable.ronaldo),
           contentDescription = null,
           contentScale = ContentScale.Crop,
           modifier = Modifier
               .height(60.dp)
               .width(60.dp)
               .clip(CircleShape)
               .border(1.dp, Color.Gray, CircleShape)
       )
   }else{
       AsyncImage(
           model = ImageRequest.Builder(LocalContext.current)
               .data(imageUrl)
               .crossfade(true)
               .build(),
           contentDescription = null,
           contentScale = ContentScale.Crop,
           modifier = Modifier
               .height(60.dp)
               .width(60.dp)
               .clip(CircleShape)
               .border(1.dp, Color.Gray, CircleShape),
           placeholder = painterResource(R.drawable.addcontact),
           error = painterResource(R.drawable.ronaldo)
       )
   }

}
