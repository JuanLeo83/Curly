import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.repository.SampleRepositoryImpl
import data.source.remote.SampleRemoteSource
import data.source.remote.SampleRemoteSourceImpl
import domain.repository.SampleRepository
import domain.usecase.SampleUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.request.RequestScreenModel

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Curly",
    ) {
        App()
    }
}

private fun initKoin() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }
}

private val dataModule = module {
    single<SampleRemoteSource> { SampleRemoteSourceImpl() }
    factory<SampleRepository>{ SampleRepositoryImpl(get()) }
}

private val domainModule = module {
    factory { SampleUseCase(get()) }
}

private val presentationModule = module {
    factory { RequestScreenModel(get()) }
}