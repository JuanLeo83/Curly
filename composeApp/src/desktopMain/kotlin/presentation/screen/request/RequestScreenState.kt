package presentation.screen.request

import domain.model.BodyType
import domain.model.RequestMethod

data class RequestScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val method: RequestMethod = RequestMethod.GET,
    val url: String = "",
    val requestParams: List<RequestParam> = listOf(),
    val headerParams: List<RequestParam> = listOf(),
    val responseViewMode: ResponseViewMode = ResponseViewMode.PRETTY,
    val responseData: ResponseData? = null
) {
    fun areAllParamsEnabled() = requestParams.all { it.isChecked }
}

data class RequestParam(
    val isChecked: Boolean = false,
    val index: Int = Int.MAX_VALUE,
    val key: String = "",
    val value: String = ""
)

data class ResponseData(
    val statusCode: String,
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
    BODY
}

sealed class TabRequestData(
    val text: String,
    val tableType: TableType
) {
    data object Params : TabRequestData("Params", TableType.PARAMS)
    data object Headers : TabRequestData("Headers", TableType.HEADERS)
}

sealed class TabResponse(val text: String) {
    data object Body : TabResponse("Body")
    data object Headers : TabResponse("Headers")
}