package presentation.screen.request.component.request

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.BodyType
import domain.model.RequestMethod
import presentation.screen.request.RequestParam
import presentation.screen.request.TableType

@Composable
fun RequestFragmentComponent(
    modifier: Modifier = Modifier,
    method: RequestMethod,
    url: String,
    requestParams: List<RequestParam>,
    headerParams: List<RequestParam>,
    requestBodyTypeSelected: BodyType,
    setRequestMethod: (RequestMethod) -> Unit,
    setUrl: (String) -> Unit,
    addRow: (TableType) -> Unit,
    onValueChange: (TableType, param: RequestParam) -> Unit,
    deleteRow: (TableType, index: Int) -> Unit,
    setRequestBodyType: (BodyType) -> Unit,
    sendRequest: () -> Unit
) {
    Column(modifier = modifier) {
        RequestUrlComponent(
            method = method,
            url = url,
            setRequestMethod = setRequestMethod,
            setUrl = setUrl,
            sendRequest = sendRequest
        )

        Spacer(modifier = Modifier.height(8.dp))

        RequestParamsComponent(
            requestParams = requestParams,
            headerParams = headerParams,
            addRow = addRow,
            onValueChange = onValueChange,
            deleteRow = deleteRow,
            requestBodyTypeSelected = requestBodyTypeSelected,
            setRequestBodyType = setRequestBodyType
        )
    }
}