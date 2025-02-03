package edu.alatoo.kyrgyzlearning.di

import edu.alatoo.kyrgyzlearning.data.repository.AppRepositoryImpl
import edu.alatoo.kyrgyzlearning.data.repository.DictionaryRepositoryImpl
import edu.alatoo.kyrgyzlearning.domain.interfaces.AppRepository
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<AppRepository> { AppRepositoryImpl(get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl() }
}