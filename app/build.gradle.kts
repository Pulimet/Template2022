plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin") // Navigation Component
}

android {
    defaultConfig {
        applicationId = "net.alexandroid.template2022"
        compileSdk = 32
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val navVersion: String by rootProject.extra
    val koinVersion = "3.1.5"

    // Modules dependencies
    implementation(project(":network"))
    implementation(project(":db"))

    // com.google
    implementation("com.google.android.material:material:1.5.0")

    // AndroidX
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // Preferences DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Koin Core features
    implementation("io.insert-koin:koin-android:$koinVersion")
    // Koin main features for Android
    implementation("io.insert-koin:koin-android:$koinVersion")
    // Koin - Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:$koinVersion")
    // Koin - Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:$koinVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Image loading library
    implementation("io.coil-kt:coil:1.1.1")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}