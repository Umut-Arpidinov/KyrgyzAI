package edu.alatoo.kyrgyzlearning.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB,
) : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected val binding: VB by lazy { bindingInflater(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
        observe()
    }

    protected open fun initialize() {}

    protected open fun observe() {}

}
