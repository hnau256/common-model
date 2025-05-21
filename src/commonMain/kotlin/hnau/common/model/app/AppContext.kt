package hnau.common.model.app

import hnau.common.model.ThemeBrightness
import hnau.common.model.color.material.MaterialHue
import hnau.common.model.preferences.Preference
import java.io.File

data class AppContext(
    val brightness: Preference<ThemeBrightness?>,
    val tryUseSystemHue: Preference<Boolean>,
    val fallbackHue: Preference<MaterialHue>,
    val filesDir: File,
)
