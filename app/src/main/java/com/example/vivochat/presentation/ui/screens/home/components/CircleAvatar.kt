import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vivochat.R

@Composable
fun CircleAvatar(
) {
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
}
