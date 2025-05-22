plugins {
    alias(libs.plugins.android.application)       // Android application plugin
    alias(libs.plugins.kotlin.android)            // Kotlin Android plugin
    alias(libs.plugins.google.gms.google.services)// Google Services plugin (for Firebase, etc.)
}

android {
    namespace = "com.example.krishaksathiandroid"  // Package namespace for your app
    compileSdk = 35                                // API level used to compile the app

    defaultConfig {
        applicationId = "com.example.krishaksathiandroid"  // Unique app ID
        minSdk = 24                               // Minimum Android version supported (Android 7.0)
        targetSdk = 35                            // Target API level
        versionCode = 1                           // Internal version number
        versionName = "1.0"                       // Human-readable version name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Specifies the test runner for instrumentation tests
    }

    buildTypes {
        release {
            isMinifyEnabled = false              // Disable code shrinking and obfuscation
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"             // ProGuard rules for release build
            )
        }
    }

    buildFeatures {
        viewBinding = true                       // Enables ViewBinding for easier UI handling
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11     // Java 11 features
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"                         // Kotlin to JVM 11 bytecode compatibility
    }
}

dependencies {
    // Core Android libraries
    implementation(libs.androidx.core.ktx)                // Kotlin extensions for core libraries
    implementation(libs.androidx.appcompat)               // AppCompat for backward compatibility
    implementation(libs.material)                         // Material Components (M3)
    implementation(libs.androidx.activity)                // Activity components
    implementation(libs.androidx.constraintlayout)        // ConstraintLayout for flexible UI

    // Firebase
    implementation(libs.firebase.database)                // Firebase Realtime Database
    implementation(libs.firebase.firestore)               // Firebase Firestore
    implementation(libs.firebase.storage)                 // Firebase Storage

    // RecyclerView for lists
    implementation(libs.androidx.recyclerview)

    // Networking (Retrofit + OkHttp + Gson)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")                // Retrofit core
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")          // Retrofit + Gson converter
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")       // Logs network requests/responses
    implementation("com.google.code.gson:gson:2.10.1")                      // Gson JSON parser

    // Unit and Instrumentation testing
    testImplementation(libs.junit)                      // JUnit for unit tests
    androidTestImplementation(libs.androidx.junit)      // AndroidX test JUnit extension
    androidTestImplementation(libs.androidx.espresso.core) // UI testing with Espresso
}
