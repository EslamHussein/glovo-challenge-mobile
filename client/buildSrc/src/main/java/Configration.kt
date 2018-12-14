package com.glovo.glovo


public object Configration {


    object Android {
        const val compileSdk = 28
        const val mindSdk = 21
        const val targetSdk = 28
        const val buildTools = "28.0.3"
        const val versionCode = 1
        const val versionName = "1.0"
        const val applicationId = "com.glovo.glovo"
    }

    object Version {
        const val androidSupportVersion = "28.0.0-alpha3"
        const val daggerVersion = "2.15"
        const val retrofitVersion = "2.3.0"
        const val okhttpVersion = "3.9.1"
        const val retrofit2RxJava2AdapterVersion = "2.3.0"
        const val constraintLayoutVersion = "1.1.2"
        const val picassoVersion = "2.71828"
        const val rxAndroidVersion = "2.0.2"
        const val rxKotlinVersion = "2.1.0"
        const val kotlinVersion = "1.2.51"
        const val junitVersion = "4.12"
        const val supportTestRunnerVersion = "1.0.2"
        const val espressoVersion = "3.0.2"
        const val mockitoVersion = "2.8.47"
        const val gradleVersion = "3.2.1"

    }


    object ClassPath {
        const val gradle = "com.android.tools.build:gradle:${Version.gradleVersion}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"

    }

    object AndroidXSupport {

        const val supportDesign = "com.android.support:design:${Version.androidSupportVersion}"
        const val appcompat = "com.android.support:appcompat-v7:${Version.androidSupportVersion}"
        const val constraintLayout =
            "com.android.support.constraint:constraint-layout:${Version.constraintLayoutVersion}"

    }

    object RX {
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroidVersion}"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Version.rxKotlinVersion}"

    }


    object Network {

        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
        const val retrofitRxJavaAdapter =
            "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit2RxJava2AdapterVersion}"
        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpVersion}"

    }


    object Kotlin {
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinVersion}"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlinVersion}"

    }


    object Testing {

        const val junit = "junit:junit:${Version.junitVersion}"
        const val supportTestRunner = "com.android.support.test:runner:${Version.supportTestRunnerVersion}"
        const val supportTestRules = "com.android.support.test:rules:${Version.supportTestRunnerVersion}"

        const val espressoCore = "com.android.support.test.espresso:espresso-core:${Version.espressoVersion}"

        const val espressoIdlingResource =
            "com.android.support.test.espresso:espresso-idling-resource:${Version.espressoVersion}"
        const val mockitoCore = "org.mockito:mockito-core:${Version.mockitoVersion}"
        const val espressoContrib = "com.android.support.test.espresso:espresso-contrib:${Version.espressoVersion}"


    }

}