package edu.alatoo.kyrgyzlearning.presentation.ui.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.databinding.FragmentSettingsBinding
import edu.alatoo.kyrgyzlearning.presentation.extensions.hide
import edu.alatoo.kyrgyzlearning.presentation.extensions.navigateWithAnimation

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
            findNavController().navigateWithAnimation(
                SettingsFragmentDirections.toAboutFragment()
            )
        }

        tvFeedback.setOnClickListener {
            findNavController().navigateWithAnimation(
                SettingsFragmentDirections.toFeedbackFragment()
            )
        }

        tvLanguage.setOnClickListener {
            findNavController().navigateWithAnimation(
                SettingsFragmentDirections.toLanguageFragment()
            )
        }

        tvTheme.setOnClickListener {
            findNavController().navigateWithAnimation(
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