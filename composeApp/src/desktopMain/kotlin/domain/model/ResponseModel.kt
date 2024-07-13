package domain.model

data class ResponseModel(
    val statusCode: Int,
    val statusDescription: String,
    val responseTime: Long,
    val size: Double,
    val type: BodyType,
    val body: String,
    val headers: List<ResponseHeader>
)

data class ResponseHeader(
    val key: String,
    val value: List<String>
)
