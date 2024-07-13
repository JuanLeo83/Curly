package presentation.common.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TabRowComponent(tabs: List<String>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        tabs.forEachIndexed { index, tab ->
            TabComponent(
                text = tab,
                selected = selectedTabIndex == index,
                index = index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun TabComponent(
    text: String,
    selected: Boolean,
    index: Int,
    onClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .widthIn(min = 64.dp)
            .background(color = if (selected) Color.LightGray else Color.Transparent)
            .clickable { onClick(index) }
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}