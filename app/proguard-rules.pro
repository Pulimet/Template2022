# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Remove all log* methods from prpject
-assumenosideeffects class net.alexandroid.template2022.utils.logs.LoggerKt { *; }

# Room
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

# Retrofit2
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

# Network models
-keepclassmembers class net.alexandroid.template2022.network.models.** { <fields>; }