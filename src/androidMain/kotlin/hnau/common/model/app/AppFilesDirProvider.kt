package hnau.common.model.app

import android.content.Context
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppFilesDirProvider(
    private val context: Context
) {

    actual fun getAppFilesDir(): File = context.filesDir

}