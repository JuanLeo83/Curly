package presentation.screen.request.component.request.url.vo

import domain.model.RequestMethod

data class UrlVo(
    val method: RequestMethod = RequestMethod.GET,
    val url: String = "",
    val setRequestMethod: (RequestMethod) -> Unit = {},
    val setUrl: (String) -> Unit = {},
    val sendRequest: () -> Unit = {}
)
