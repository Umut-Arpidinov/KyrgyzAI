package edu.alatoo.kyrgyzlearning.presentation.ui.home

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.NavMainDirections
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentHomeBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModel()


    override fun initialize() {
        super.initialize()
        initToolbar()

    }

    override fun onLoading(loading: Boolean) {
        super.onLoading(loading)
        binding.progress.isVisible = loading
    }

    override fun initClicks() = with(binding) {
        super.initClicks()
        btnOpenScanner.setOnClickListener {
            findNavController().navigate(
                NavMainDirections.toObjectDetectionFragment()
            )
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
    }


    private fun initToolbar() = with(binding) {
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Learning"
        }

    }


}