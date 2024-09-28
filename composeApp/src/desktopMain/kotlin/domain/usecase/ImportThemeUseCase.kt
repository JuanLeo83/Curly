package domain.usecase

import domain.repository.ConfigRepository

class ImportThemeUseCase(private val repository: ConfigRepository) {
    operator fun invoke(path: String): Result<Unit> {
        return repository.importTheme(path)
    }
}