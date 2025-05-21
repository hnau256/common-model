package hnau.common.model.preferences

import arrow.core.Option
import kotlinx.coroutines.flow.StateFlow

data class Preference<T>(
    val value: StateFlow<Option<T>>,
    val update: suspend (newValue: T) -> Unit,
)