package hnau.common.model.app

import hnau.common.model.file.File
import kotlinx.io.files.Path

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppFilesDirProvider {

    actual fun getAppFilesDir(): File = File(Path(""))
}