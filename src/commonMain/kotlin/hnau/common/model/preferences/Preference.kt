package hnau.common.model.preferences

import kotlinx.coroutines.flow.StateFlow

data class Preference<T>(
    val value: StateFlow<T>,
    val update: suspend (newValue: T) -> Unit,
)