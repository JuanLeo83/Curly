package presentation.screen.request.component.request

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.model.BodyType

@Composable
fun RequestBodyComponent(
    modifier: Modifier = Modifier,
    optionSelected: BodyType,
    setRequestBodyType: (BodyType) -> Unit
) {
    Column {
        BodyTypeDropdownComponent(
            optionSelected = optionSelected,
        ) { setRequestBodyType(it) }
    }
}