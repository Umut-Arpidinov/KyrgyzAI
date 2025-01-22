plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
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
    ksp(libs.roomCompiler)

    //test libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(project(":object-detection"))




}