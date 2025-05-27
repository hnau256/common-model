package hnau.common.model.file

import kotlinx.io.RawSink
import kotlinx.io.RawSource
import kotlinx.io.files.Path

fun File.exists(): Boolean =
    fileSystem.exists(path)

fun File.source(): RawSource =
    fileSystem.source(path)

fun File.sink(): RawSink =
    fileSystem.sink(path)

inline fun File.map(
    transformPath: (Path) -> Path,
): File = copy(
    path = transformPath(path),
)

operator fun File.plus(
    pathPart: String,
): File = map { path ->
    Path(path, pathPart)
}