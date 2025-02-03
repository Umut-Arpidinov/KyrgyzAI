package edu.alatoo.kyrgyzlearning.presentation.ui.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentSettingsBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide

class SettingsFragment: BaseFragment<SettingsViewModel, FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    override val viewModel: SettingsViewModel by viewModels()

    override fun initialize() {
        super.initialize()
        initToolbar()
    }

    override fun initClicks() = with(binding){
        super.initClicks()

        tvAbout.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.toAboutFragment()
            )
        }

        tvFeedback.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.toFeedbackFragment()
            )
        }

        tvLanguage.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.toLanguageFragment()
            )
        }

        tvTheme.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.toThemeFragment()
            )
        }

    }

    override fun observeViewModel() {
        super.observeViewModel()
    }



    private fun initToolbar() = with(binding){
        toolbar.apply {
            ivBack.hide()
            toolbarTitle.text = "Settings"
        }
    }



}