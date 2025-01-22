plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "edu.alatoo.kyrgyzlearning.object_detection"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Coroutines
    implementation(libs.kotlinStdLib)
    implementation(libs.kotlinStdLibJdk)
    implementation(libs.coroutinesPlayServices)
    implementation(libs.coroutinesCore)



    // Object detection libs
    implementation(libs.tensorflow)
    implementation(libs.arcore)
    implementation(libs.objectDetection)
    implementation(libs.customObjectDetection)
    platform(libs.googleLibrariesBom)
    implementation(libs.googleCloudVision)
    implementation(libs.objLoader)

}