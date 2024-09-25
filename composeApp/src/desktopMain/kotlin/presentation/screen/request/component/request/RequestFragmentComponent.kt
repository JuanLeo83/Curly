package presentation.screen.request.component.request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.screen.request.component.request.authorization.vo.AuthVo
import presentation.screen.request.component.request.body.vo.RequestBodyVo
import presentation.screen.request.component.request.param.vo.ParamTableVo
import presentation.screen.request.component.request.url.RequestUrlComponent
import presentation.screen.request.component.request.url.vo.UrlVo

@Composable
fun RequestFragmentComponent(
    modifier: Modifier = Modifier,
    urlVo: UrlVo,
    paramsVo: ParamTableVo,
    headersVo: ParamTableVo,
    authVo: AuthVo,
    bodyVo: RequestBodyVo
) {
    Column(modifier = modifier) {
        RequestUrlComponent(urlVo)

        Spacer(modifier = Modifier.height(8.dp))

        RequestTabsComponent(
            paramsVo = paramsVo,
            headersVo = headersVo,
            authVo = authVo,
            bodyVo = bodyVo
        )
    }
}