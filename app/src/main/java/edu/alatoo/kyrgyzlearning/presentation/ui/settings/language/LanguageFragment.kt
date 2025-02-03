package edu.alatoo.kyrgyzlearning.presentation.ui.settings.language

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentLanguageBinding
import edu.alatoo.kyrgyzlearning.presentation.ui.settings.SettingsViewModel


class LanguageFragment: BaseFragment<SettingsViewModel, FragmentLanguageBinding>(
    FragmentLanguageBinding::inflate
) {
    override val viewModel: SettingsViewModel by viewModels()

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
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            toolbarTitle.text = "App language"
        }
    }



}