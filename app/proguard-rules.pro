# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Softwares\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Hide warnings about references to newer platforms in the library
-verbose

-dontwarn android.support.v7.**
-dontwarn com.mastercard.terminalsdk.**
-dontwarn com.google.gson.**

# don't process support library
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keepattributes Exceptions,InnerClasses,Signature,Deprecated, SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# ---------------Begin: proguard configuration for Gson  ----------
# Gson specific classes
-keep class sun.misc.Unsafe {
    <fields>;
    <methods>;
}
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** {
    <fields>;
    <methods>;
}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# ---------------End: proguard configuration for Gson  ----------

# ===APP 1=== BEGIN: Do not re-obfuscate Terminal SDK Jar ======
-keep class com.mastercard.terminalsdk.** {*;}
-keep class com.mastercard.terminalsdk.**$** {*;}
-keep interface com.mastercard.terminalsdk.** { *; }
-keep enum com.mastercard.terminalsdk.** { *; }
# ===APP 1=== END: Do not re-obfuscate Terminal SDK Jar ======

# ===APP 2=== BEGIN: Do not obfuscate references to GSON ======
-keep class com.google.gson.**{*;}
# ===APP 2=== END: Do not obfuscate references to GSON ======




