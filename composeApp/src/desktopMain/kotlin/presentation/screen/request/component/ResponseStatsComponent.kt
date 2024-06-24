package presentation.screen.request.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp

@Composable
fun ResponseStatsComponent(
    statusCode: String,
    responseTime: String,
    size: String
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Public,
            contentDescription = "Network icon",
            modifier = Modifier.width(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Status: ")
        Text(text = statusCode)
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Timer,
            contentDescription = "Timer icon",
            modifier = Modifier.width(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Time: ")
        Text(text = responseTime)
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.FileDownload,
            contentDescription = "Size icon",
            modifier = Modifier.width(24.dp)
        )
        Text(text = "Size: ")
        Text(text = size)
    }
}