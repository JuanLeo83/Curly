package presentation.screen.request

import domain.model.BodyType
import domain.model.RequestMethod

data class RequestScreenState(
    val isLoading: Boolean = false,
    val method: RequestMethod = RequestMethod.GET,
    val url: String = "",
    val errorMessage: String = "",
    val responseViewMode: ResponseViewMode = ResponseViewMode.PRETTY,
    val responseData: ResponseData? = null
)

data class ResponseData(
    val statusCode: String,
    val responseTime: String,
    val size: String,
    val type: BodyType,
    val rawBody: String,
    val body: String
)

enum class ResponseViewMode {
    PRETTY,
    RAW
}