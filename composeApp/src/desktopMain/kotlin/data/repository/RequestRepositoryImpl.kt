package data.repository

import domain.model.RequestParams
import domain.model.RequestResult
import domain.repository.RequestRepository

class RequestRepositoryImpl : RequestRepository {
    override suspend fun doRequest(params: RequestParams): Result<RequestResult> {
        return Result.success(
            RequestResult(
                statusCode = 200,
                responseTime = 151,
                size = 1.71,
                body = """
                    {
                        "message": "Hello, World!"
                        "data": {
                            "id": 1,
                            "name": "John Doe",
                            "email": "johndoe@mail.com"
                    }
                """.trimIndent()
            )
        )
    }
}