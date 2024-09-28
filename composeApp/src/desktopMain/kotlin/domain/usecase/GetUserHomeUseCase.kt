package domain.usecase

import domain.repository.ConfigRepository

class GetUserHomeUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<String> {
        return repository.getUserHome()
    }
}