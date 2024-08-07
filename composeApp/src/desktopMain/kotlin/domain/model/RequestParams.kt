package domain.model

data class RequestParams(
    val method: RequestMethod,
    val url: String,
    val headers: List<RequestHeader>
)

data class RequestHeader(
    val key: String,
    val value: String
)
