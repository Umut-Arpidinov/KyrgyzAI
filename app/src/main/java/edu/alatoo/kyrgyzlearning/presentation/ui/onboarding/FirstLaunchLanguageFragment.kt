package edu.alatoo.kyrgyzlearning.presentation.ui.onboarding

import androidx.navigation.fragment.findNavController
import edu.alatoo.kyrgyzlearning.NavWelcomeDirections
import edu.alatoo.kyrgyzlearning.R
import edu.alatoo.kyrgyzlearning.common.base.BaseFragment
import edu.alatoo.kyrgyzlearning.common.utils.ENGLISH
import edu.alatoo.kyrgyzlearning.common.utils.KYRGYZ
import edu.alatoo.kyrgyzlearning.common.utils.RUSSIAN
import edu.alatoo.kyrgyzlearning.databinding.FragmentFirstLaunchLanguageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstLaunchLanguageFragment :
    BaseFragment<OnBoardingViewModel, FragmentFirstLaunchLanguageBinding>(
        FragmentFirstLaunchLanguageBinding::inflate
    ) {

    override val viewModel: OnBoardingViewModel by viewModel()


    override fun initialize() {
        super.initialize()
    }

    override fun initClicks() = with(binding) {
        super.initClicks()
        radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbKyrgyz -> {
                    viewModel.setLanguage(KYRGYZ)
                }

                R.id.rbRussian -> {
                    viewModel.setLanguage(RUSSIAN)
                }

                R.id.rbEnglish -> {
                    viewModel.setLanguage(ENGLISH)
                }
            }
            btnNext.isEnabled = true
        }

        btnNext.setOnClickListener {
            viewModel.setFirstLaunchCompleted()
            navigateToMainNav()
        }

    }

    override fun observeViewModel() {
        super.observeViewModel()
    }


    private fun navigateToMainNav() {
        findNavController()
            .navigate(NavWelcomeDirections.toNavMain())
    }


}