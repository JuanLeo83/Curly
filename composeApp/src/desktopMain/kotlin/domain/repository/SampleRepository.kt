package domain.repository

import domain.model.SampleModel

interface SampleRepository {
    suspend fun getMessage(): Result<SampleModel>
}