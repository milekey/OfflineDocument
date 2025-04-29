plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.scaredeer.offlinedocument"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.scaredeer.offlinedocument"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "20250429"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("com.google.android.material:material:1.12.0")
}