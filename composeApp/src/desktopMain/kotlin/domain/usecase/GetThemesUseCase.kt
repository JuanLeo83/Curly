package domain.usecase

import domain.repository.ConfigRepository

class GetThemesUseCase(private val repository: ConfigRepository) {
    operator fun invoke(): Result<List<String>> {
        return repository.getAllThemes()
    }
}