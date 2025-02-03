package edu.alatoo.kyrgyzlearning.presentation.extensions

import android.app.Activity
import android.view.WindowManager


fun Activity.setAdjustNothing() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
}