package hnau.common.model

fun String.toEditingString(): EditingString =
    EditingString(text = this)
