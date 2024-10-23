package presentation.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ThemeTextUnit(
    val url: UrlTextUnit = UrlTextUnit(),
    val button: TextUnit = 12.sp,
    val input: InputTextUnit = InputTextUnit(),
    val tab: TextUnit = 12.sp,
    val checkbox: TextUnit = 12.sp,
    val requestTypes: RequestTypesTextUnit = RequestTypesTextUnit(),
    val dropdown: TextUnit = 12.sp,
    val body: TextUnit = 12.sp,
    val table: TableTextUnit = TableTextUnit(),
    val responseStats: TextUnit = 12.sp,
    val sidebar: TextUnit = 8.sp,
    val responseHeaders: ResponseHeadersTextUnit = ResponseHeadersTextUnit(),
)

data class UrlTextUnit(
    val text: TextUnit = 14.sp,
    val placeholder: TextUnit = 14.sp
)

data class InputTextUnit(
    val label: TextUnit = 12.sp,
    val text: TextUnit = 13.sp,
    val placeholder: TextUnit = 14.sp
)

data class RequestTypesTextUnit(
    val optionSelected: TextUnit = 14.sp,
    val option: TextUnit = 12.sp
)

data class TableTextUnit(
    val header: TextUnit = 12.sp,
    val row: TextUnit = 12.sp
)

data class ResponseHeadersTextUnit(
    val key: TextUnit = 12.sp,
    val value: TextUnit = 12.sp
)
