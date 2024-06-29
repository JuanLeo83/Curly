package presentation.screen.request.component.request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.RequestMethod
import presentation.screen.request.RequestParam
import presentation.screen.request.TableType

@Composable
fun RequestFragmentComponent(
    method: RequestMethod,
    url: String,
    requestParams: List<RequestParam>,
    headerParams: List<RequestParam>,
    setRequestMethod: (RequestMethod) -> Unit,
    setUrl: (String) -> Unit,
    addRow: (TableType) -> Unit,
    onValueChange: (TableType, param: RequestParam) -> Unit,
    deleteRow: (TableType, index: Int) -> Unit,
    sendRequest: () -> Unit
) {
    Column {
        RequestUrlComponent(
            method = method,
            url = url,
            setRequestMethod = setRequestMethod,
            setUrl = setUrl,
            sendRequest = sendRequest
        )

        Spacer(modifier = Modifier.height(16.dp))

        RequestParamsComponent(
            requestParams = requestParams,
            headerParams = headerParams,
            addRow = addRow,
            onValueChange = onValueChange,
            deleteRow = deleteRow
        )
    }
}