plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "edu.alatoo.kyrgyzlearning"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.alatoo.kyrgyzlearning"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/INDEX.LIST"

        }
    }

}

dependencies {



    // Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    // Coroutines
    implementation(libs.kotlinStdLib)
    implementation(libs.kotlinStdLibJdk)
    implementation(libs.coroutinesPlayServices)
    implementation(libs.coroutinesCore)

    // Object detection libs
    implementation(libs.arcore)

    // Room dependencies
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    //test libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Koin
    implementation(libs.koinAndroid)
    implementation(libs.koinCore)
    implementation(libs.koinNavigation)

    // Navigation
    implementation(libs.navUi)
    implementation(libs.navCommon)
    implementation(libs.navFragment)

    implementation(libs.timber)

    // Network
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logginInterceptor)
    implementation(libs.chucker)


    api(project(":object-detection"))




}