package presentation.screen.request.component.request.param

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import domain.model.BodyType
import kotlinx.coroutines.launch
import presentation.common.component.tab.TabContentComponent
import presentation.common.component.tab.TabRowComponent
import presentation.screen.request.RequestParam
import presentation.screen.request.TabRequestData
import presentation.screen.request.TableType
import presentation.screen.request.component.request.body.RequestBodyComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestParamsComponent(
    requestParams: List<RequestParam>,
    headerParams: List<RequestParam>,
    requestBodyTypeSelected: BodyType,
    requestBodyValue: String,
    addRow: (TableType) -> Unit = {},
    onValueChange: (TableType, RequestParam) -> Unit = { _, _ -> },
    deleteRow: (TableType, index: Int) -> Unit = { _, _ -> },
    setRequestBodyType: (BodyType) -> Unit,
    setRequestBody: (String) -> Unit
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

                TabRequestData.Body -> RequestBodyComponent(
                    optionSelected = requestBodyTypeSelected,
                    bodyValue = requestBodyValue,
                    setRequestBodyType = setRequestBodyType,
                    setBody = setRequestBody
                )
            }
        }
    }
}

