package edu.alatoo.kyrgyzlearning.presentation.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.domain.interfaces.AppRepository

class MainViewModel(
    private val appRepository: AppRepository
): BaseViewModel() {

    private val _isFirstLaunch: MutableLiveData<Boolean> = MutableLiveData()
    val isFirstLaunch: LiveData<Boolean> get() = _isFirstLaunch

    init {
        _isFirstLaunch.value = appRepository.isFirstLaunch
    }

}