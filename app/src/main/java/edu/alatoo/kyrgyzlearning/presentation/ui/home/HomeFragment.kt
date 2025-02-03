package edu.alatoo.kyrgyzlearning.presentation.ui.home

import androidx.fragment.app.viewModels
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentHomeBinding
import edu.alatoo.kyrgyzlearning.databinding.LayoutToolbarBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.setAdjustNothing

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()


    override fun initialize() {
        super.initialize()
        initToolbar()

    }

    override fun initClicks() {
        super.initClicks()
    }

    override fun observeViewModel() {
        super.observeViewModel()
    }


    private fun initToolbar() = with(binding){
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Learning"
        }

    }




}