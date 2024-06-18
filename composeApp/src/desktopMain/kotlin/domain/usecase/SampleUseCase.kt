package domain.usecase

import domain.model.SampleModel
import domain.repository.SampleRepository

class SampleUseCase(private val repository: SampleRepository) {
    suspend operator fun invoke(): Result<SampleModel> = repository.getMessage()
}