package presentation.screen.request

import domain.model.BodyType
import domain.model.RequestMethod

data class RequestScreenState(
    val method: RequestMethod = RequestMethod.GET,
    val url: String = "",
    val errorMessage: String = "",
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