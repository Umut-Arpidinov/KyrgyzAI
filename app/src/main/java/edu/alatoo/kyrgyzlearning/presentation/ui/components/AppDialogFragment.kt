package edu.alatoo.kyrgyzlearning.presentation.ui.components

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import edu.alatoo.kyrgyzlearning.common.base.BaseDialogFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentAppDialogBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.show

class AppDialogFragment :
    BaseDialogFragment<FragmentAppDialogBinding>(FragmentAppDialogBinding::inflate) {


    private val dialogType: String? get() = arguments?.getString(DIALOG_TYPE)
    private val message: String? get() =  arguments?.getString(ARG_MESSAGE)

    private var onYeasActionClick: (() -> Unit)? = null
    private var onNoActionClick: (() -> Unit)? = null
    private var onSimpleOkActionClick: (() -> Unit)? = null

    override fun initialize() = with(binding) {
        super.initialize()
        tvMessage.text = message
        setDialogViewsByType()
    }

    override fun initClicks() = with(binding){
        super.initClicks()
        btnYes.setOnClickListener {
            onYeasActionClick?.invoke()
            dismiss()
        }
        btnNo.setOnClickListener {
            onNoActionClick?.invoke()
            dismiss()

        }

        btnOk.setOnClickListener {
            onSimpleOkActionClick?.invoke()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialogViewParams()
    }

    companion object {
        private const val ARG_MESSAGE = "dialog_message"
        private const val DIALOG_TYPE = "dialog_type"
        fun newInstance(
            message: String,
            dialogType: String
        ): AppDialogFragment {
            val dialog = AppDialogFragment()
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            args.putString(DIALOG_TYPE, dialogType)
            dialog.arguments = args
            return dialog
        }
    }


    private fun setDialogViewsByType() = with(binding) {
        when (dialogType) {
            SIMPLE_DIALOG -> {
                llActions.hide()
                btnOk.show()
            }

            DIALOG_WITH_ACTION -> {
                llActions.show()
                btnOk.hide()
            }
        }
    }

    fun showSimpleDialog(manager: FragmentManager, actionOk: (() -> Unit)? = null) {
        if (!isAdded && !isVisible) {
            onSimpleOkActionClick = actionOk
            show(manager, dialogType)
        }
    }

    fun disableDismissOutside(){
        isCancelable = false
    }

    fun showDialogWithActions(
        manager: FragmentManager,
        actionYeas: () -> Unit,
        actionNo: () -> Unit
    ) {
        if (!isAdded) {
            onYeasActionClick = actionYeas
            onNoActionClick = actionNo
            show(manager, dialogType)
        }
    }

    private fun dialogViewParams() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }
}