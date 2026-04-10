plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.quizapp"
    compileSdk = 35   // updated to match AGP 8.7.3 recommended SDK

    defaultConfig {
        applicationId = "com.example.quizapp"
        minSdk        = 24
        targetSdk     = 35   // updated
        versionCode   = 1
        versionName   = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}