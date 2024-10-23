package presentation.common.component.tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun TabContentComponent(
    pagerState: PagerState,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(top = 4.dp)
    ) { index ->
        content(index)
    }
}