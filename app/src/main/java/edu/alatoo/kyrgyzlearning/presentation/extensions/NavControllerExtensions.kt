package edu.alatoo.kyrgyzlearning.presentation.extensions

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import edu.alatoo.kyrgyzlearning.R


fun NavController.navigateWithAnimation(navDirections: NavDirections, args: Bundle? = null) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)    // Animation when entering the destination
        .setExitAnim(R.anim.slide_out_left)       // Animation when exiting the current fragment
        .setPopEnterAnim(R.anim.slide_in_left)    // Animation when returning (pop enter)
        .setPopExitAnim(R.anim.slide_out_right)   // Animation when popping the back stack (pop exit)
        .build()
    this.navigate(navDirections, navOptions)
}