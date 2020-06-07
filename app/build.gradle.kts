import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.test.tiket"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                )
            }
        }
    }

    viewBinding {
        isEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType < KotlinCompile > ().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Dependencies.kotlin)

    // Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Android
    implementation(Android.appcompat)
    implementation(Android.ktx)
    implementation(Android.constraintLayout)
    implementation(Android.paging)

    // Architecture Components
    implementation(Dependencies.viewModel)
    implementation(Dependencies.liveData)

    // Material Design
    implementation(Dependencies.materialDesign)

    // Coil-kt
    implementation(Dependencies.coil)

    //ThreetenAbp
    implementation(Dependencies.threetenAbp)

    //Timber
    implementation(Dependencies.timber)

    //Lottie
    implementation(Dependencies.lottie)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp)

    // Moshi
    implementation(Moshi.kotlin)
    implementation(Moshi.retrofitConverter)
    kapt(Moshi.codeGen)

    // Dagger 2
    implementation(Dagger.dagger)
    kapt(Dagger.compiler)
    implementation(Dagger.android)
    implementation(Dagger.support)
    kapt(Dagger.processor)

    // Testing
    testImplementation(Testing.core)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.okHttp)
    testImplementation(Testing.jUnit)

    // Android Testing
    androidTestImplementation(Testing.extJUnit)
    androidTestImplementation(Testing.espresso)
}
