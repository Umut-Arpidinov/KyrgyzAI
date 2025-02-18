package edu.alatoo.kyrgyzlearning.presentation.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import edu.alatoo.kyrgyzlearning.presentation.ui.components.AppDialogFragment
import edu.alatoo.kyrgyzlearning.presentation.ui.components.DIALOG_WITH_ACTION
import edu.alatoo.kyrgyzlearning.presentation.ui.components.SIMPLE_DIALOG
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


fun Fragment.showSimpleDialog(@StringRes message: Int, actionOk: () -> Unit) {
    val mMessage = getString(message)
    AppDialogFragment.newInstance(mMessage, SIMPLE_DIALOG)
        .showSimpleDialog(childFragmentManager, actionOk)
}

fun Fragment.showSimpleDialog(message: String, actionOk: () -> Unit) {
    AppDialogFragment.newInstance(message, SIMPLE_DIALOG)
        .showSimpleDialog(childFragmentManager, actionOk)
}



fun Fragment.showDialogWithActions(
    @StringRes message: Int, actionYeas: () -> Unit, actionNo: () -> Unit
) {
    val mMessage = getString(message)
    AppDialogFragment.newInstance(mMessage, DIALOG_WITH_ACTION)
        .showDialogWithActions(
            manager = childFragmentManager,
            actionYeas = actionYeas,
            actionNo = actionNo
        )
}


