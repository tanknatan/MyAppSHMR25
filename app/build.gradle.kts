plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.natan.shamilov.shmr25"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.natan.shamilov.shmr25"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Dagger 2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    // navigation
    implementation(libs.androidx.navigation.compose)
    // Material
    implementation(libs.material3)
    // Lottie
    implementation(libs.lottie)
    // Splash
    implementation(libs.splash)
    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.codegen)
    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Feature modules
    implementation(project(":feature:account"))
    implementation(project(":feature:categories"))
    implementation(project(":feature:expenses"))
    implementation(project(":feature:history"))
    implementation(project(":feature:incomes"))
    implementation(project(":feature:options"))
    implementation(project(":feature:splash"))
    // Общий модуль
    implementation(project(":common"))
}
