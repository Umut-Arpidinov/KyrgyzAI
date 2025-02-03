package edu.alatoo.kyrgyzlearning

import android.app.Application
import edu.alatoo.kyrgyzlearning.di.appModule
import edu.alatoo.kyrgyzlearning.di.repositoryModule
import edu.alatoo.kyrgyzlearning.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ProjectApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProjectApplication)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    viewModelModule,

                )
            )
        }
        Timber.plant(Timber.DebugTree())
    }

}