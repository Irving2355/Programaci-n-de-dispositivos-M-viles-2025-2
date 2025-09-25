plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.icc.practica6"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.icc.practica6"
        minSdk = 34
        targetSdk = 36
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Parse Android SDK (desde JitPack)
    implementation ("com.github.parse-community.Parse-SDK-Android:parse:4.3.0")

    // RecyclerView (1.4.0 requiere compilar con API 35+)
    implementation ("androidx.recyclerview:recyclerview:1.4.0")

    // Material Components (botones, etc.)
    implementation ("com.google.android.material:material:1.12.0")

    // Glide para imágenes remotas
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // Kotlin coroutines (opcional, para llamadas asíncronas ordenadas)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
}