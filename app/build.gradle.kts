plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.hirecruiterapp2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hirecruiterapp2"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled=true // Enable MultiDex
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

     implementation("com.google.firebase:firebase-bom:32.8.0")
   implementation("com.google.android.gms:play-services-auth:21.0.0")
   implementation("com.firebaseui:firebase-ui-auth:7.2.0")
   implementation("com.google.firebase:firebase-database:20.3.1")


    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    val lifecycleversion ="2.7.0"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleversion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleversion")

    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.dhaval2404:imagepicker:2.1")


    implementation ("androidx.multidex:multidex:2.0.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //gson-converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
}