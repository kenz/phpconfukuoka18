-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
#Databinding
-dontwarn android.databinding.**
#Kotlin
-dontwarn kotlin.**
# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-dontwarn sun.misc.Unsafe
-dontwarn com.octo.android.robospice.retrofit.RetrofitJackson**
-dontwarn retrofit.appengine.UrlFetchClient
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-dontwarn android.net.http.AndroidHttpClient
-dontwarn retrofit.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
   long producerNode;
   long consumerNode;
}


-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn okhttp3.**
-keep class okhttp3.*{ *; }
-keep interface okhttp3.*{ *; }
-keepnames class android.support.design.internal.BottomNavigationMenuView{*; }
-dontwarn afu.org.checkerframework.**
-keep class com.squareup.okhttp.*ok* { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit.appengine.UrlFetchClient
-keep class retrofit.** { *; }

-dontwarn com.google.errorprone.annotations.**
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keepattributes *Annotation*
-keepattributes SourceFile, LineNumberTable

-keepattributes Signature
-keepattributes *Annotation*
-keep class java.lang.ClassValue.** {*; }
-keep interface java.lang.ClassValue.** {*; }
-dontwarn java.lang.ClassValue