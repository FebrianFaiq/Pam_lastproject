plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.pam_lastproject"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.example.pam_lastproject"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        // Gunakan tanda sama dengan (=) untuk assignment di Kotlin DSL
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Dependensi Firebase
    // Gunakan kurung () untuk pemanggilan fungsi implementation
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))

    // Tambahkan dependensi untuk Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Retrofit dan Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide (untuk memuat gambar dari URL)
    implementation("com.github.bumptech.glide:glide:4.16.0")

}