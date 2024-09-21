package presentation.screen.request.component.request.body

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.BodyType

@Composable
fun RequestBodyComponent(
    modifier: Modifier = Modifier,
    optionSelected: BodyType,
    bodyValue: String,
    setRequestBodyType: (BodyType) -> Unit,
    setBody: (String) -> Unit
) {
    Row {
        BodyTypeDropdownComponent(
            optionSelected = optionSelected,
        ) { setRequestBodyType(it) }

        Spacer(modifier = Modifier.width(8.dp))

        BodyFormComponent(
            modifier = Modifier.fillMaxWidth(),
            optionSelected = optionSelected,
            value = bodyValue,
            setBody = setBody
        )
    }
}