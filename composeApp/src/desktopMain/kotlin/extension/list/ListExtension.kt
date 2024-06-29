package extension.list

import presentation.screen.request.RequestParam

fun List<RequestParam>.add(value: RequestParam): List<RequestParam> {
    return toMutableList().apply { add(value) }.sortRequestParams()
}

fun List<RequestParam>.modify(value: RequestParam, index: Int = -1): List<RequestParam> {
    return if (index in 0..lastIndex) {
        toMutableList().apply { set(index, value) }.sortRequestParams()
    } else throw IndexOutOfBoundsException("Index out of bounds: index = $index -> lastIndex = $lastIndex")
}

fun List<RequestParam>.remove(index: Int): List<RequestParam> {
    return if (index in 0..lastIndex) {
        toMutableList().apply { removeAt(index) }.sortRequestParams()
    } else throw IndexOutOfBoundsException("Index out of bounds: index = $index -> lastIndex = $lastIndex")
}


fun List<RequestParam>.sortRequestParams(): List<RequestParam> {
    return sortedBy { it.index }.mapIndexed { index, requestParam ->
        requestParam.copy(index = index)
    }
}