package domain.usecase

import domain.repository.ConfigRepository

class GetConfigUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<Unit> {
        return Result.success(Unit)
    }
}