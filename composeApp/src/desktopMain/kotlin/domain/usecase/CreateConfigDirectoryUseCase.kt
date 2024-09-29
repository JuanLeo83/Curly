package domain.usecase

import domain.repository.ConfigRepository

class CreateConfigDirectoryUseCase(private val repository: ConfigRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.createConfigDirectory()
    }
}