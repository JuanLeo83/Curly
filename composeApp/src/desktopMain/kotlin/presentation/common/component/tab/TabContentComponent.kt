package presentation.common.component.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContentComponent(
    pagerState: PagerState,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        verticalAlignment = Alignment.Top
    ) { index ->
        content(index)
    }
}