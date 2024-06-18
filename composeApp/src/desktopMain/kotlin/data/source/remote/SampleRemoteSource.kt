package data.source.remote

import domain.model.SampleModel

interface SampleRemoteSource {
    fun getMessage(): Result<SampleModel>
}

class SampleRemoteSourceImpl : SampleRemoteSource {
    override fun getMessage(): Result<SampleModel> {
        return Result.success(SampleModel("Hello, World!"))
    }
}