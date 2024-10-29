import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

val properties = gradleLocalProperties(rootDir, providers)
val apiKey: String = properties.getProperty("API_KEY")

android {
    namespace = "com.jeon.rest_api"
    compileSdk = 34
    android.buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", getApiKey("API_KEY"))
    }

    buildFeatures{
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun getApiKey(key: String): String{
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

dependencies {

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    //Retrofit
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.gsonconverter)
    implementation(libs.retrofit.rxjava2)

    //okhttp
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(project(":model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}