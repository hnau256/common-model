package hnau.common.model.file

import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

data class File(
    val path: Path,
    val fileSystem: FileSystem = SystemFileSystem,
)