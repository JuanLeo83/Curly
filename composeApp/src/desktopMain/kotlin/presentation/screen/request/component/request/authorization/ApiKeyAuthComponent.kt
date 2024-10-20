package presentation.screen.request.component.request.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.component.dropdown.DropdownComponent
import presentation.common.component.input.InputField
import presentation.screen.request.component.request.authorization.vo.ApiKeyAuthVo

@Composable
fun ApiKeyAuthComponent(
    vo: ApiKeyAuthVo
) {
    Row {
        DropdownComponent(
            modifier = Modifier.width(150.dp),
            options = vo.options,
            optionSelected = vo.optionSelected.value,
            onOptionSelected = { vo.onOptionSelected(it) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(0.5f)) {
            InputField(
                label = "Key",
                value = vo.key,
                onValueChange = vo.onKeyChange,
                placeholder = "Enter key"
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                label = "Value",
                value = vo.value,
                onValueChange = vo.onValueChange,
                placeholder = "Enter value"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}