package edu.alatoo.kyrgyzlearning.di

import edu.alatoo.kyrgyzlearning.presentation.ui.activities.MainViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.DictionaryViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.dictionary.wordDetail.WordDetailViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.home.HomeViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.main.MainFragmentViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.objectDetection.ObjectDetectionViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.onboarding.OnBoardingViewModel
import edu.alatoo.kyrgyzlearning.presentation.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { OnBoardingViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { MainFragmentViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { ObjectDetectionViewModel(get()) }
    viewModel { DictionaryViewModel(get()) }
    viewModel { WordDetailViewModel(get(),get(),get()) }

}