package hnau.common.model.app

import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppFilesDirProvider {

    fun getAppFilesDir(): File
}