package presentation.screen.request

import domain.model.AuthorizationType
import domain.model.BodyType
import domain.model.RequestMethod
import presentation.screen.request.component.request.authorization.ApiKeyAddTo
import presentation.screen.request.component.request.authorization.model.AuthVo

data class RequestScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val method: RequestMethod = RequestMethod.GET,
    val url: String = "",
    val requestParams: List<RequestParam> = listOf(),
    val headerParams: List<RequestParam> = listOf(),
    val requestBodyType: BodyType = BodyType.NONE,
    val requestBodyValue: String = "",
    val requestAuthorizationType: AuthorizationType = AuthorizationType.NONE,
    val apiKeyAddTo: ApiKeyAddTo = ApiKeyAddTo.HEADERS,
    val responseViewMode: ResponseViewMode = ResponseViewMode.PRETTY,
    val responseData: ResponseData? = null,
    val authVo: AuthVo = AuthVo()
)

data class RequestParam(
    val isChecked: Boolean = true,
    val index: Int = Int.MAX_VALUE,
    val key: String = "",
    val value: String = ""
)

data class ResponseData(
    val status: String,
    val responseTime: String,
    val size: String,
    val type: BodyType,
    val rawBody: String,
    val body: String,
    val headers: Map<String, String>
)

enum class ResponseViewMode {
    PRETTY,
    RAW
}

enum class TableType {
    PARAMS,
    HEADERS,
    COOKIES,
    BODY,
    AUTHORIZATION
}

sealed class TabRequestData(
    val text: String,
    val tableType: TableType
) {
    data object Params : TabRequestData("Params", TableType.PARAMS)
    data object Headers : TabRequestData("Headers", TableType.HEADERS)
    data object Body : TabRequestData("Body", TableType.BODY)
    data object Authorization : TabRequestData("Authorization", TableType.AUTHORIZATION)
}

sealed class TabResponse(val text: String) {
    data object Body : TabResponse("Body")
    data object Headers : TabResponse("Headers")
}