package presentation.common.component.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var showPassword by remember { mutableStateOf(false) }

    InputField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        trailingIcon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
        onTrailingIconClick = { showPassword = !showPassword },
        trailingIconVisibility = TrailingIconVisibility.Condition(value.isNotEmpty()),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
    )
}