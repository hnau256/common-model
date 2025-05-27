package hnau.common.model.app

import hnau.common.model.ThemeBrightness
import hnau.common.model.color.material.MaterialHue
import hnau.common.model.goback.GoBackHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.KSerializer

data class AppSeed<M, S>(
    val defaultBrightness: ThemeBrightness? = null,
    val fallbackHue: MaterialHue,
    val skeletonSerializer: KSerializer<S>,
    val createDefaultSkeleton: () -> S,
    val createModel: (CoroutineScope, AppContext, S) -> M,
    val extractGoBackHandler: (M) -> GoBackHandler,
)