package edu.alatoo.kyrgyzlearning.di

import android.content.Context
import android.content.res.Resources
import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import edu.alatoo.kyrgyzlearning.common.utils.ErrorConverter
import edu.alatoo.kyrgyzlearning.data.local.LocalDataSource
import edu.alatoo.kyrgyzlearning.data.local.LocalDataSourceImpl
import edu.alatoo.kyrgyzlearning.data.local.db.KyrgyzLearningDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SHARED_PREFERENCES_NAME = "edu.alatoo.word"

val appModule = module {

    single { KyrgyzLearningDataBase.getDataBaseClient(androidContext())}
    factory { get<KyrgyzLearningDataBase>().dictionaryDao() }
    single { androidContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<Resources> { androidApplication().resources }
    single { ErrorConverter(get()) }
    single<GenerativeModel> {
        Firebase.vertexAI.generativeModel("gemini-1.5-flash")
    }
}
