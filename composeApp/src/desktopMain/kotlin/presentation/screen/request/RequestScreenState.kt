package presentation.screen.request

import domain.model.AuthorizationType
import domain.model.BodyType
import domain.model.RequestMethod
import presentation.screen.request.component.request.authorization.vo.ApiKeyAddTo
import presentation.screen.request.component.request.authorization.vo.AuthVo
import presentation.screen.request.component.request.body.vo.RequestBodyVo
import presentation.screen.request.component.request.param.vo.ParamTableVo
import presentation.screen.request.component.request.url.vo.UrlVo

data class RequestScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val responseViewMode: ResponseViewMode = ResponseViewMode.PRETTY,
    val responseData: ResponseData? = null,
    val urlVo: UrlVo = UrlVo(),
    val paramsVo: ParamTableVo = ParamTableVo(tableType = TableType.PARAMS),
    val headersVo: ParamTableVo = ParamTableVo(tableType = TableType.HEADERS),
    val authVo: AuthVo = AuthVo(),
    val bodyVo: RequestBodyVo = RequestBodyVo()
) {
    fun setUrl(url: String) = copy(urlVo = urlVo.copy(url = url))

    fun setMethod(method: RequestMethod) = copy(urlVo = urlVo.copy(method = method))

    fun setBodyType(bodyType: BodyType) = copy(bodyVo = bodyVo.copy(optionSelected = bodyType))

    fun setBody(body: String) = copy(bodyVo = bodyVo.copy(value = body))

    fun setAuthorizationType(type: AuthorizationType) =
        copy(authVo = authVo.copy(optionSelected = type))

    fun setBasicUserName(userName: String) = copy(authVo = authVo.setBasicUserName(userName))

    fun setBasicPassword(password: String) = copy(authVo = authVo.setBasicPassword(password))

    fun setBearerToken(token: String) = copy(authVo = authVo.setBearerToken(token))

    fun setApiKeyAddTo(optionSelected: ApiKeyAddTo) =
        copy(authVo = authVo.setApiKeyAddTo(optionSelected))

    fun setApiKey(key: String) = copy(authVo = authVo.setApiKey(key))

    fun setApiKeyValue(value: String) = copy(authVo = authVo.setApiKeyValue(value))

    fun addParamSocket() = copy(paramsVo = paramsVo.addParamSocket())

    fun addHeaderSocket() = copy(headersVo = headersVo.addParamSocket())

    fun modifyHeaderSocket(param: RequestParam) = copy(headersVo = headersVo.modifyParamSocket(param))

    fun deleteHeaderSocket(index: Int) = copy(headersVo = headersVo.deleteParamSocket(index))
}

data class RequestParam(
    val isChecked: Boolean = true,
    val index: Int = Int.MAX_VALUE,
    val key: String = "",
    val value: String = ""
)

data class ResponseData(
    val status: String,
    val responseTime: String,
    val size: String,
    val type: BodyType,
    val rawBody: String,
    val body: String,
    val headers: Map<String, String>
)

enum class ResponseViewMode {
    PRETTY,
    RAW
}

enum class TableType {
    PARAMS,
    HEADERS,
    COOKIES,
    BODY,
    AUTHORIZATION
}

sealed class TabRequestData(
    val text: String,
    val tableType: TableType
) {
    data object Params : TabRequestData("Params", TableType.PARAMS)
    data object Headers : TabRequestData("Headers", TableType.HEADERS)
    data object Body : TabRequestData("Body", TableType.BODY)
    data object Authorization : TabRequestData("Authorization", TableType.AUTHORIZATION)
}

sealed class TabResponse(val text: String) {
    data object Body : TabResponse("Body")
    data object Headers : TabResponse("Headers")
}