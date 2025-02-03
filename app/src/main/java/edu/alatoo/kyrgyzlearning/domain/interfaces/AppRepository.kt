package edu.alatoo.kyrgyzlearning.domain.interfaces

interface AppRepository {

    var isFirstLaunch: Boolean

    var appLanguage: String?
}