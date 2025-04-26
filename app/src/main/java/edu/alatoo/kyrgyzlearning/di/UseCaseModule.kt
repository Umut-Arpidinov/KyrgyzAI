package edu.alatoo.kyrgyzlearning.di

import edu.alatoo.kyrgyzlearning.domain.usecases.GetLastFiveWordsUseCase
import edu.alatoo.kyrgyzlearning.domain.usecases.GetWordByIdUseCase
import edu.alatoo.kyrgyzlearning.domain.usecases.GetWordsFromDbUseCase
import edu.alatoo.kyrgyzlearning.domain.usecases.SaveWordsUseCase
import org.koin.dsl.module


val useCaseModule = module {
    factory { SaveWordsUseCase(get()) }

    single { GetWordsFromDbUseCase(get()) }

    single { GetWordByIdUseCase(get()) }

    single { GetLastFiveWordsUseCase(get()) }
}