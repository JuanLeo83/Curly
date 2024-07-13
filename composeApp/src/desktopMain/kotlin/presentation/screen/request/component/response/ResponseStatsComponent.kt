package presentation.screen.request.component.response

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResponseStatsComponent(
    statusCode: String,
    responseTime: String,
    size: String
) {

    fun colorizeStatus(status: String): Color {
        return if (status.startsWith("2")) Color.Green else Color.Red
    }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Public,
            contentDescription = "Network icon",
            modifier = Modifier.width(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Status: ", fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = statusCode,
            fontSize = 12.sp,
            color = colorizeStatus(statusCode),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Timer,
            contentDescription = "Timer icon",
            modifier = Modifier.width(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Time: ", fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = responseTime,
            fontSize = 12.sp,
            color = Color.Green,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.FileDownload,
            contentDescription = "Size icon",
            modifier = Modifier.width(16.dp)
        )
        Text(text = "Size: ", fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = size,
            fontSize = 12.sp,
            color = Color.Green,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}