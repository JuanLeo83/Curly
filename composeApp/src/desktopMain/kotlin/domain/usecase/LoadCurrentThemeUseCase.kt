package domain.usecase

import domain.model.AppTheme
import domain.repository.ConfigRepository

class LoadCurrentThemeUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<AppTheme> {
        return repository.loadCurrentTheme()
    }
}