package domain.usecase

import domain.model.ThemeModel
import domain.repository.ConfigRepository

class ApplyThemeUseCase(private val repository: ConfigRepository) {
    suspend operator fun invoke(themeName: String): Result<ThemeModel> {
        return repository.setTheme(themeName)
    }
}