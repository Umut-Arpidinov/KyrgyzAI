package edu.alatoo.kyrgyzlearning.presentation.ui.onboarding

import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.domain.interfaces.AppRepository

class OnBoardingViewModel(
    private val appRepository: AppRepository
) : BaseViewModel(){


    fun setLanguage(language: String) {
        appRepository.appLanguage = language
    }

    fun setFirstLaunchCompleted(){
        appRepository.isFirstLaunch = false
    }


}