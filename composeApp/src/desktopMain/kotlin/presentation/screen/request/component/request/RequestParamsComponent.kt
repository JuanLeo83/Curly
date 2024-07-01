package presentation.screen.request.component.request

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import presentation.common.component.tab.TabContentComponent
import presentation.common.component.tab.TabRowComponent
import presentation.screen.request.RequestParam
import presentation.screen.request.TabRequestData
import presentation.screen.request.TableType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestParamsComponent(
    requestParams: List<RequestParam>,
    headerParams: List<RequestParam>,
    addRow: (TableType) -> Unit = {},
    onValueChange: (TableType, RequestParam) -> Unit = { _, _ -> },
    deleteRow: (TableType, index: Int) -> Unit = { _, _ -> }
) {
    val tabs = listOf(TabRequestData.Params, TabRequestData.Headers, TabRequestData.Authorization, TabRequestData.Body)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column {
        TabRowComponent(
            tabs = tabs.map { it.text },
            selectedTabIndex = pagerState.currentPage
        ) { index ->
            scope.launch {
                pagerState.animateScrollToPage(index)
            }
        }

        TabContentComponent(pagerState) { index ->
            when (tabs[index]) {
                TabRequestData.Params -> ParamTableComponent(
                    tabs[index].tableType,
                    requestParams,
                    addRow,
                    onValueChange,
                    deleteRow
                )

                TabRequestData.Headers -> ParamTableComponent(
                    tabs[index].tableType,
                    headerParams,
                    addRow,
                    onValueChange,
                    deleteRow
                )

                TabRequestData.Authorization -> Text("Authorization here!")

                TabRequestData.Body -> Text("Body here!")
            }
        }
    }
}

