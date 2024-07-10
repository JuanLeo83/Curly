package presentation.screen.request.component.response

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import presentation.common.component.tab.TabContentComponent
import presentation.common.component.tab.TabRowComponent
import presentation.screen.request.ResponseData
import presentation.screen.request.ResponseViewMode
import presentation.screen.request.TabResponse

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResponseFragmentComponent(
    response: ResponseData,
    viewMode: ResponseViewMode,
    showPretty: () -> Unit,
    showRaw: () -> Unit
) {
    val scrollState = rememberScrollState()
    val tabs = listOf(TabResponse.Body, TabResponse.Headers)
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TabRowComponent(
                tabs = tabs.map { it.text },
                selectedTabIndex = pagerState.currentPage
            ) { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }

            ResponseStatsComponent(
                statusCode = response.statusCode,
                responseTime = response.responseTime,
                size = response.size
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            TabContentComponent(pagerState) { index ->
                when (tabs[index]) {
                    TabResponse.Body -> ResponseBodyComponent(
                        response = response,
                        viewMode = viewMode,
                        showRaw = showRaw,
                        showPretty = showPretty,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .verticalScroll(scrollState)
                    )

                    TabResponse.Headers -> {
                        Table(response.headers)
                    }
                }
            }
        }
    }
}

@Composable
fun Table(data: Map<String, String>) {
    SelectionContainer {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Divider(color = Color.Gray, thickness = 0.5.dp)
            data.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = key,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                    )
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        fontSize = 16.sp
                    )
                }
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    }
}