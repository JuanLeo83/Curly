package presentation.screen.request.component.request.url

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.screen.request.component.request.url.vo.UrlVo

@Composable
fun RequestUrlComponent(vo: UrlVo) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            )
    ) {
        RequestMethodDropdownComponent(
            modifier = Modifier.weight(0.2f),
            optionSelected = vo.method
        ) { vo.setRequestMethod(it) }

        Divider(
            modifier = Modifier
                .background(Color.LightGray)
                .height(28.dp)
                .width(1.dp)
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = vo.url,
            onValueChange = { vo.setUrl(it) },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.weight(0.6f)
                .focusRequester(focusRequester)
                .onPreviewKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                        vo.sendRequest()
                        focusManager.clearFocus(force = true)
                        true
                    } else false
                },
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.CenterStart) {
                    innerTextField()
                    if (vo.url.isEmpty()) {
                        Text(
                            "Enter URL...",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { vo.sendRequest() },
            modifier = Modifier.requiredWidth(120.dp).fillMaxHeight()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Send")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Request"
                )
            }
        }
    }
}