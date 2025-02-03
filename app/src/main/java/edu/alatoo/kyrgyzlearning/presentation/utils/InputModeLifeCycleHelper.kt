package edu.alatoo.kyrgyzlearning.presentation.utils

import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import edu.alatoo.kyrgyzlearning.presentation.extensions.getSoftInputMode

class InputModeLifeCycleHelper(
    private var window: Window?,
    private val mode: Mode
) : DefaultLifecycleObserver {


    private var originalMode: Int = SOFT_INPUT_STATE_UNSPECIFIED

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        setNewSoftInputMode()

    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        restoreOriginalSoftInputMode()
    }

    private fun setNewSoftInputMode() {
        window?.let {
            originalMode = it.getSoftInputMode()
            it.setSoftInputMode(
                when (mode) {
                    Mode.ADJUST_RESIZE -> WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                    Mode.ADJUST_NOTHING -> WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
                    Mode.ADJUST_PAN -> WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                }
            )
        }
    }

    private fun restoreOriginalSoftInputMode() {
        window?.setSoftInputMode(originalMode)
        window = null
    }

    enum class Mode {
        ADJUST_RESIZE,
        ADJUST_NOTHING,
        ADJUST_PAN
    }
}