plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt") // Added to support Room
}

android {
    defaultConfig {
        minSdk = 21
        targetSdk = 32
        compileSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
    namespace = "net.alexandroid.template2022.db"
}

dependencies {
    val roomVersion = "2.4.2"
    implementation(project(":network"))

    implementation("androidx.core:core-ktx:1.8.0")

    // Room
    api("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // GSON
    implementation("com.google.code.gson:gson:2.9.0")
}