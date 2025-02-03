package edu.alatoo.kyrgyzlearning.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import edu.alatoo.kyrgyzlearning.common.utils.ErrorConverter
import edu.alatoo.kyrgyzlearning.databinding.LayoutToolbarBinding
import org.koin.android.ext.android.inject

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var loading = false

    private val errorConverter: ErrorConverter by inject()


    private var _binding: VB? = null

    protected val binding
        get() = _binding!!

    protected abstract val viewModel: VM




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoadingState()
        initialize()
        initClicks()
        observeViewModel()
        observeErrorState()

    }


    protected open fun initialize() {

    }

    protected open fun initClicks() {}

    protected open fun observeViewModel() {

    }


    private fun observeErrorState() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                if (it.throwable is NoSuchElementException)
                    return@observe
                onError(errorConverter.convert(it.throwable))
                onError(it.throwable)
            }
        }
    }


    private fun observeLoadingState() {
        viewModel.loading.observe(viewLifecycleOwner) {
            val anyLoading = viewModel.loading.value == true
            if (loading != anyLoading) {
                loading = anyLoading
                onLoading(loading)
            }
        }
    }

    protected open fun onLoading(loading: Boolean) {
        this.loading = loading
    }


    protected fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, length).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected open fun onError(throwable: Throwable) {}

    protected open fun onError(message: String) {}


}
