package presentation.common.component.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import presentation.screen.settings.SettingsScreen
import theme

@Composable
fun SidebarComponent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    navigator: Navigator,
    onLoadTheme: () -> Unit,
    onExpandClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .width(if (isExpanded) 80.dp else 50.dp)
            .background(color = theme.colors.table.header.background)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)) {
            SidebarItemComponent(
                imageVector = Icons.AutoMirrored.Outlined.Send,
                title = "Request",
                showTitle = isExpanded
            ) {
                navigator.push(SettingsScreen(onLoadTheme))
            }
            SidebarItemComponent(
                imageVector = Icons.Outlined.Settings,
                title = "Settings",
                showTitle = isExpanded
            ) {
                navigator.push(SettingsScreen(onLoadTheme))
            }
        }

        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Outlined.ChevronLeft else Icons.Outlined.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onExpandClick() },
                tint = theme.colors.table.header.text
            )
        }
    }
}

@Composable
private fun SidebarItemComponent(
    imageVector: ImageVector,
    title: String,
    showTitle: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = theme.colors.table.header.text
        )
        if (showTitle) {
            Text(
                text = title,
                fontSize = 8.sp,
                color = theme.colors.table.header.text,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}