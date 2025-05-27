package hnau.common.model.app.utils

import arrow.core.getOrElse
import arrow.core.toOption
import hnau.common.kotlin.mapper.Mapper
import hnau.common.kotlin.mapper.nameToEnum
import hnau.common.kotlin.mapper.nullable
import hnau.common.kotlin.mapper.stringToBoolean
import hnau.common.model.ThemeBrightness
import hnau.common.model.app.AppContext
import hnau.common.model.color.material.MaterialHue
import hnau.common.model.preferences.impl.FileBasedPreferences
import hnau.common.model.preferences.map
import hnau.common.model.preferences.mapOption
import hnau.common.model.preferences.withDefault
import kotlinx.coroutines.CoroutineScope
import java.io.File

internal suspend fun AppContext(
    scope: CoroutineScope,
    defaultBrightness: ThemeBrightness?,
    defaultTryUseSystemHue: Boolean,
    fallbackHue: MaterialHue,
    filesDir: File,
): AppContext {
    val preferences = FileBasedPreferences
        .Factory(
            preferencesFile = File(filesDir, "common_preferences.txt")
        )
        .createPreferences(
            scope = scope,
        )
    return AppContext(
        brightness = preferences["brightness"]
            .mapOption(
                scope = scope,
                mapper = Mapper
                    .nameToEnum<ThemeBrightness>()
                    .nullable
                    .let { mapper ->
                        Mapper(
                            direct = { nameOrNone ->
                                nameOrNone
                                    .map(mapper.direct)
                                    .getOrElse { defaultBrightness }
                            },
                            reverse = { brightnessOrNull ->
                                brightnessOrNull
                                    ?.let(mapper.reverse)
                                    .toOption()
                            },
                        )
                    },
            ),
        tryUseSystemHue = preferences["try_use_system_hue"]
            .map(
                scope = scope,
                mapper = Mapper.stringToBoolean,
            )
            .withDefault(
                scope = scope
            ) { defaultTryUseSystemHue },
        fallbackHue = preferences["fallback_hue"]
            .map(
                scope = scope,
                mapper = Mapper
                    .nameToEnum<MaterialHue>(),
            )
            .withDefault(
                scope = scope
            ) { fallbackHue },
        filesDir = filesDir,
    )
}