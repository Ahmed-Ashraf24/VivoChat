package com.example.vivochat.presentation.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivochat.R
import com.example.vivochat.presentation.ui.theme.onlineColor


@Composable
fun ChatBottomBar(modifier: Modifier = Modifier, message: String) {
    Row(modifier = modifier
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Surface(modifier = Modifier.weight(1f),color = onlineColor.copy(alpha = .1f), shape = RoundedCornerShape(15.dp)) {
            BasicTextField(
                modifier=Modifier.padding(vertical = 8.dp),
                value = message,
                onValueChange = {},
                decorationBox = { innerTextField ->
                    Row(modifier=Modifier.padding(horizontal = 10.dp)) {
                    if (message.isEmpty()) {
                        Text("Send Message", color = Color.Gray)
                    } else innerTextField()
                    }

                }

            )
        }
        Icon(
            painter = painterResource(R.drawable.attach_ic),
            "attach",
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Icon(
            painter = painterResource(R.drawable.send_ic),
            "attach",
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatBottomBarPrev() {
    ChatBottomBar(modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp),message = "")
}