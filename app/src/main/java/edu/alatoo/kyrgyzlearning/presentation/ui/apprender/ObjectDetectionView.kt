package edu.alatoo.kyrgyzlearning.presentation.ui.apprender

import AppRenderer
import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.helpers.SnackbarHelper
import edu.alatoo.kyrgyzlearning.common.samplerender.SampleRender
import edu.alatoo.kyrgyzlearning.databinding.FragmentObjectDetectionBinding

class ObjectDetectionView(val context: Context, renderer: AppRenderer) : DefaultLifecycleObserver {

    val binding = FragmentObjectDetectionBinding.inflate(LayoutInflater.from(context))

    val surfaceView = binding.surfaceview.apply {
        SampleRender(this, renderer, context.assets)
    }

    val snackBarHelper = SnackbarHelper().apply {
        setParentView(binding.coordinatorLayout)
        setMaxLines(6)
    }

    override fun onResume(owner: LifecycleOwner) {
        surfaceView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        surfaceView.onPause()
    }


    fun post(action: Runnable) = binding.root.post(action)



    fun setScanningActive(active: Boolean) = with(binding) {
        when(active) {
            true -> {
                scanButton.isEnabled = false
                scanButton.setText(R.string.scan_busy)
            }

            false -> {
                scanButton.isEnabled = true
                scanButton.setText(R.string.scan_available)
            }
        }

    }

}
