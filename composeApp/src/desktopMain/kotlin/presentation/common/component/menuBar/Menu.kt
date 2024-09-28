package presentation.common.component.menuBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

@Composable
fun FrameWindowScope.Menu(isOpen: Boolean) {
    if (!isOpen) return

    MenuBar {
        Menu("File", mnemonic = 'F') {
            Item("Settings",
                onClick = {  },
                shortcut = KeyShortcut(Key.Comma, meta = true)
            )
        }
    }
}