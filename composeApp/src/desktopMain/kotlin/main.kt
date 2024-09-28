import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.repository.ConfigRepositoryImpl
import data.repository.RequestRepositoryImpl
import data.source.local.ConfigLocalSource
import data.source.local.ConfigLocalSourceImpl
import data.source.remote.RemoteMapper
import data.source.remote.RequestRemoteSource
import data.source.remote.RequestRemoteSourceImpl
import domain.repository.ConfigRepository
import domain.repository.RequestRepository
import domain.usecase.CreateConfigDirectoryUseCase
import domain.usecase.DoRequestUseCase
import domain.usecase.GetUserHomeUseCase
import domain.usecase.ImportThemeUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.request.RequestScreenMapper
import presentation.screen.request.RequestScreenModel
import presentation.screen.settings.SettingsScreenModel
import java.awt.Dimension

fun main() = application {
    initKoin()

    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1280.dp, 768.dp)
    )

    var isOpen by remember { mutableStateOf(true) }

    fun exitApp() {
        isOpen = false
        exitApplication()
    }

    Window(
        state = state,
        onCloseRequest = ::exitApp,
        title = "Curly",
    ) {
        window.minimumSize = Dimension(800, 600)

//        Menu(isOpen)

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
    single<ConfigLocalSource> { ConfigLocalSourceImpl() }
    factory<ConfigRepository> { ConfigRepositoryImpl(get()) }
}

private val domainModule = module {
    factory { CreateConfigDirectoryUseCase(get()) }
    factory { DoRequestUseCase(get()) }
    factory { GetUserHomeUseCase(get()) }
    factory { ImportThemeUseCase(get()) }
}

private val presentationModule = module {
    factory { RequestScreenModel(get(), get(), get()) }
    factory { RequestScreenMapper() }
    factory { SettingsScreenModel(get(), get()) }
}