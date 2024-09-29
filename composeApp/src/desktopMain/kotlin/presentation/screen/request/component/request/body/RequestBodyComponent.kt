package presentation.screen.request.component.request.body

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.component.dropdown.DropdownComponent
import presentation.screen.request.component.request.body.vo.RequestBodyVo

@Composable
fun RequestBodyComponent(
    modifier: Modifier = Modifier,
    vo: RequestBodyVo
) {
    Row {
        DropdownComponent(
            modifier = Modifier.width(150.dp),
            options = vo.options,
            optionSelected = vo.optionSelected.value,
            onOptionSelected = { vo.onOptionSelected(it) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        BodyFormComponent(
            modifier = Modifier.fillMaxWidth(),
            optionSelected = vo.optionSelected,
            value = vo.value,
            setBody = vo.setBody
        )
    }
}