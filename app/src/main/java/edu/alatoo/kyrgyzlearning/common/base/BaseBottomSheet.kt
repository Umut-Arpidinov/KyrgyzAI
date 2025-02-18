package edu.alatoo.kyrgyzlearning.common.base

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.alatoo.kyrgyzlearning.R

abstract class BaseBottomSheet<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            setBottomSheetLayoutParams(bottomSheet)
            expandBottomSheet(behavior)
        }
    }

    @SuppressLint("InternalInsetResource")
    private fun setBottomSheetLayoutParams(bottomSheet: FrameLayout) {
        when (bottomSheetType()) {
            BottomSheetType.FULL_SCREEN -> {
                val layoutParams = bottomSheet.layoutParams
                val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
                var statusHeight = 0
                if (resourceId > 0) {
                    statusHeight = resources.getDimensionPixelSize(resourceId)
                }
                val navResourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
                var navHeight = 0
                if (resourceId > 0) {
                    navHeight = resources.getDimensionPixelSize(navResourceId)
                }
                val fullHeight = activity?.window?.decorView?.height ?: 0
                layoutParams.height = if(fullHeight == 0) Resources.getSystem().displayMetrics.heightPixels
                else fullHeight - statusHeight - navHeight
                bottomSheet.layoutParams = layoutParams
            }
            BottomSheetType.WRAP_CONTENT -> {
                //Nothing
            }
        }
    }

    private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        initClicks()
        observeViewModel()
    }

    protected open fun initialize() {}

    protected open fun initClicks() {}

    protected open fun observeViewModel() {}

    abstract fun bottomSheetType(): BottomSheetType

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}