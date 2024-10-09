package domain.usecase

import domain.model.AppTheme
import domain.repository.ConfigRepository

class ApplyThemeUseCase(private val repository: ConfigRepository) {
    suspend operator fun invoke(themeName: String): Result<AppTheme> {
        return repository.setTheme(themeName)
    }
}