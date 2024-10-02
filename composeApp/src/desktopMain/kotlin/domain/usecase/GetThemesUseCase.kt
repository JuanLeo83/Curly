package domain.usecase

import domain.model.ThemesModel
import domain.repository.ConfigRepository

class GetThemesUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<ThemesModel> {
        return repository.getAllThemes()
    }
}