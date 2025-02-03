package edu.alatoo.kyrgyzlearning.data.local

import android.content.SharedPreferences
import edu.alatoo.kyrgyzlearning.data.local.db.KyrgyzLearningDataBase

class LocalDataSourceImpl(
    private val preferences: SharedPreferences,
    private val appDp: KyrgyzLearningDataBase
) : LocalDataSource {

    override var chosenNativeLanguage: String?
        get() = preferences.getString(NATIVE_LANGUAGE, null)
        set(nativeLanguage) {
            preferences.edit().putString(NATIVE_LANGUAGE, nativeLanguage).apply()
        }

    override var isFirstLaunch: Boolean
        get() = preferences.getBoolean(IS_FIRST_LAUNCH, true)
        set(isFirstLaunch) {
            preferences.edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply()
        }


    companion object {
        private const val NATIVE_LANGUAGE = "edu.alatoo.native_language"
        private const val IS_FIRST_LAUNCH = "edu.alatoo.first_launch"

    }
}