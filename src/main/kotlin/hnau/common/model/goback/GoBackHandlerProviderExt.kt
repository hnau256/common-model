package hnau.common.model.goback

import hnau.common.kotlin.coroutines.flatMapState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

fun StateFlow<GoBackHandlerProvider>.stateGoBackHandler(
    scope: CoroutineScope,
): GoBackHandler = flatMapState(scope) { it.goBackHandler }
