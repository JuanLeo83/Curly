package domain.model

data class RequestParams(
    val method: RequestMethod,
    val url: String,
    val headers: List<RequestHeader>,
    val authorization: Authorization,
    val bodyType: BodyType,
    val body: String
)

data class RequestHeader(
    val key: String,
    val value: String
)

data class Authorization(
    val type: AuthorizationType,
    val basic: BasicAuthorization?,
    val bearer: BearerAuthorization?,
    val apiKey: ApiKeyAuthorization?
)

data class BasicAuthorization(
    val userName: String,
    val password: String
)

data class BearerAuthorization(
    val token: String
)

data class ApiKeyAuthorization(
    val addTo: ApiKeyAddTo,
    val key: String,
    val value: String
)
