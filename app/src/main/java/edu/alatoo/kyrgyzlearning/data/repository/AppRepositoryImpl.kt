package edu.alatoo.kyrgyzlearning.data.repository

import edu.alatoo.kyrgyzlearning.data.local.LocalDataSource
import edu.alatoo.kyrgyzlearning.domain.interfaces.AppRepository

class AppRepositoryImpl(
    private val localDataSource: LocalDataSource
): AppRepository {


    override var isFirstLaunch: Boolean
        get() = localDataSource.isFirstLaunch
        set(isFirstLaunch) {
            localDataSource.isFirstLaunch = isFirstLaunch
        }

    override var appLanguage: String?
        get() = localDataSource.chosenNativeLanguage
        set(appLanguage) {
            localDataSource.chosenNativeLanguage = appLanguage
        }
}