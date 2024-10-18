package domain.usecase

import domain.model.ThemeModel
import domain.repository.ConfigRepository

class LoadCurrentThemeUseCase(private val repository: ConfigRepository) {
    suspend operator fun invoke(): Result<ThemeModel> {
        return repository.loadCurrentTheme()
    }
}