package edu.alatoo.kyrgyzlearning.presentation.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.Window
import androidx.core.view.isVisible


fun View.gone(animate: Boolean = false, duration: Long = 300) {
    hide(View.GONE, animate, duration)

}

private fun View.hide(hidingStrategy: Int, animate: Boolean = true, duration: Long = 300) {
    if (animate) {
        animate().alpha(0f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
            }
        })
    } else {
        visibility = hidingStrategy
    }
}


fun View.visible(animate: Boolean = false, duration: Long = 300) {
    if (animate) {
        animate().alpha(1f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}

fun Window.getSoftInputMode(): Int {
    return attributes.softInputMode
}


fun View.hide() {
    isVisible = false
}
fun View.show() {
    isVisible = true
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

