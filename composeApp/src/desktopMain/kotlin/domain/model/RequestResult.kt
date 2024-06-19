package domain.model

data class RequestResult(
    val statusCode: Int,
    val responseTime: Long,
    val size: Double,
    val body: String
)
