package presentation.screen.request.component.request

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import presentation.common.component.tab.TabContentComponent
import presentation.common.component.tab.TabRowComponent
import presentation.screen.request.TabRequestData
import presentation.screen.request.component.request.authorization.RequestAuthorizationComponent
import presentation.screen.request.component.request.authorization.vo.AuthVo
import presentation.screen.request.component.request.body.RequestBodyComponent
import presentation.screen.request.component.request.body.vo.RequestBodyVo
import presentation.screen.request.component.request.param.ParamTableComponent
import presentation.screen.request.component.request.param.vo.ParamTableVo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestTabsComponent(
    paramsVo: ParamTableVo,
    headersVo: ParamTableVo,
    authVo: AuthVo,
    bodyVo: RequestBodyVo
) {
    val tabs = listOf(
        TabRequestData.Params,
        TabRequestData.Headers,
        TabRequestData.Authorization,
        TabRequestData.Body
    )
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
                TabRequestData.Params -> ParamTableComponent(paramsVo)
                TabRequestData.Headers -> ParamTableComponent(headersVo)
                TabRequestData.Authorization -> RequestAuthorizationComponent(authVo)
                TabRequestData.Body -> RequestBodyComponent(vo = bodyVo)
            }
        }
    }
}

