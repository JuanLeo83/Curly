import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.repository.RequestRepositoryImpl
import data.source.remote.RemoteMapper
import data.source.remote.RequestRemoteSource
import data.source.remote.RequestRemoteSourceImpl
import domain.repository.RequestRepository
import domain.usecase.DoRequestUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.request.RequestScreenMapper
import presentation.screen.request.RequestScreenModel
import java.awt.Dimension

fun main() = application {
    initKoin()

    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1280.dp, 768.dp)
    )

    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "Curly",
    ) {
        window.minimumSize = Dimension(800, 600)

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
    factory { RemoteMapper() }
    single<RequestRemoteSource> { RequestRemoteSourceImpl(get()) }
    factory<RequestRepository> { RequestRepositoryImpl(get()) }
}

private val domainModule = module {
    factory { DoRequestUseCase(get()) }
}

private val presentationModule = module {
    factory { RequestScreenModel(get(), get()) }
    factory { RequestScreenMapper() }
}