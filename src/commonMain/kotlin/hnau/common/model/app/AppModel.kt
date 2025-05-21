package hnau.common.model.app

import hnau.common.kotlin.Loadable
import hnau.common.kotlin.LoadableStateFlow
import hnau.common.kotlin.coroutines.flatMapState
import hnau.common.kotlin.coroutines.mapWithScope
import hnau.common.kotlin.fold
import hnau.common.kotlin.map
import hnau.common.kotlin.mapper.Mapper
import hnau.common.kotlin.mapper.toMapper
import hnau.common.model.app.utils.AppContext
import hnau.common.model.goback.GoBackHandler
import hnau.common.model.goback.NeverGoBackHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class AppModel<M, S>(
    scope: CoroutineScope,
    savedState: SavedState,
    appFilesDirProvider: AppFilesDirProvider,
    defaultTryUseSystemHue: Boolean,
    seed: AppSeed<M, S>,
) {

    private val modelSkeletonMapper: Mapper<String, S> =
        json.toMapper(seed.skeletonSerializer)

    private val modelSkeleton: S = savedState
        .savedState
        ?.let(modelSkeletonMapper.direct)
        ?: seed.createDefaultSkeleton()

    data class State<M>(
        val context: AppContext,
        val model: M,
    )

    val state: StateFlow<Loadable<State<M>>> = LoadableStateFlow(
        scope = scope,
    ) {
        AppContext(
            scope = scope,
            defaultBrightness = seed.defaultBrightness,
            defaultTryUseSystemHue = defaultTryUseSystemHue,
            fallbackHue = seed.fallbackHue,
            filesDir = appFilesDirProvider.getAppFilesDir(),
        )
    }.mapWithScope(scope) { modelScope, appContextOrLoading ->
        appContextOrLoading.map { appContext ->
            State(
                context = appContext,
                model = seed.createModel(modelScope, appContext, modelSkeleton),
            )
        }
    }

    val savableState: SavedState
        get() = modelSkeletonMapper.reverse(modelSkeleton).let(::SavedState)

    val goBackHandler: GoBackHandler = state.flatMapState(scope) { modelOrLoading ->
        modelOrLoading.fold(
            ifLoading = { NeverGoBackHandler },
            ifReady = { seed.extractGoBackHandler(it.model) },
        )
    }

    companion object {

        private val json: Json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
}