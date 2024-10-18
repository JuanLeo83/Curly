package presentation.common.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme

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
    var width by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .widthIn(min = 64.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = if (index == 0) 4.dp else 0.dp,
                    topEnd = if (index == 3) 4.dp else 0.dp
                )
            )
            .onGloballyPositioned {
                width = with(density) {
                    it.size.width.toDp()
                }
            }
            .clickable { onClick(index) }
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        if (selected) {
            Spacer(
                modifier = Modifier
                    .height(3.dp)
                    .width(width)
                    .background(color = theme.colors.secondary)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}