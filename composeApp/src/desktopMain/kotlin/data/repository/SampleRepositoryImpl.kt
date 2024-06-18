package data.repository

import data.source.remote.SampleRemoteSource
import domain.model.SampleModel
import domain.repository.SampleRepository

class SampleRepositoryImpl(private val source: SampleRemoteSource) : SampleRepository {

    override suspend fun getMessage(): Result<SampleModel> {
        return source.getMessage()
    }

}