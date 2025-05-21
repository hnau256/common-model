package hnau.common.model.preferences

import arrow.core.Option
import arrow.core.identity
import arrow.core.some
import hnau.common.kotlin.coroutines.mapState
import hnau.common.kotlin.mapper.Mapper
import kotlinx.coroutines.CoroutineScope

fun <I, O> Preference<Option<I>>.map(
    scope: CoroutineScope,
    mapper: Mapper<I, O>,
): Preference<Option<O>> = Preference<Option<O>>(
    value = value.mapState(scope) { valueOrNone ->
        valueOrNone.map(mapper.direct)
    },
    update = { newValue ->
        val transformedNewValue = newValue.map(mapper.reverse)
        update(transformedNewValue)
    }
)

inline fun <T> Preference<Option<T>>.withDefault(
    scope: CoroutineScope,
    crossinline default: () -> T,
): Preference<T> = Preference<T>(
    value = value.mapState(scope) { valueOrNone ->
        valueOrNone.fold(
            ifEmpty = default,
            ifSome = ::identity
        )
    },
    update = { newValue -> update(newValue.some()) }
)