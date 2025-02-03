package edu.alatoo.kyrgyzlearning.presentation.extensions

import androidx.fragment.app.Fragment
import edu.alatoo.kyrgyzlearning.presentation.utils.InputModeLifeCycleHelper

fun Fragment.setAdjustNothing() {
    viewLifecycleOwner
        .lifecycle
        .addObserver(
            InputModeLifeCycleHelper(
                window = activity?.window,
                InputModeLifeCycleHelper.Mode.ADJUST_NOTHING
            )
        )
}

fun Fragment.setAdjustResize() {
    viewLifecycleOwner
        .lifecycle
        .addObserver(
            InputModeLifeCycleHelper(
                window = activity?.window,
                InputModeLifeCycleHelper.Mode.ADJUST_RESIZE
            )
        )
}

