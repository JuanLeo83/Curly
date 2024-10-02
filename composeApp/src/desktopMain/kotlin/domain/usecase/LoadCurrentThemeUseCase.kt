package domain.usecase

import domain.repository.ConfigRepository

class LoadCurrentThemeUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<Unit> {
        return repository.loadCurrentTheme()
    }
}