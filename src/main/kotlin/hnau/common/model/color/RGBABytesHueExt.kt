package hnau.common.model.color

import hnau.common.model.color.gradient.Gradient
import hnau.common.model.color.gradient.create

private val hueGradientLocal: Gradient<RGBABytes> = Gradient.create(
    RGBABytes.Red,
    RGBABytes.Yellow,
    RGBABytes.Green,
    RGBABytes.Cyan,
    RGBABytes.Blue,
    RGBABytes.Magenta,
    RGBABytes.Red,
)

val RGBABytes.Companion.hueGradient: Gradient<RGBABytes>
    get() = hueGradientLocal