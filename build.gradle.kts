// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.4.1" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.10" apply false
}

buildscript {
    val navVersion by extra { "2.4.2" }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}