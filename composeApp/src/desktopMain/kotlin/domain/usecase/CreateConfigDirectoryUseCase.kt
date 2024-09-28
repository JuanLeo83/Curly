package domain.usecase

import domain.repository.ConfigRepository

class CreateConfigDirectoryUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<Unit> {
        return repository.createConfigDirectory()
    }
}