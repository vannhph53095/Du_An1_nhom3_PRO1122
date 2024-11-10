plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "fpoly.ph53095.nhom3_du_an_1_pro1122"
    compileSdk = 34

    defaultConfig {
        applicationId = "fpoly.ph53095.nhom3_du_an_1_pro1122"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Sử dụng BOM cho Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.0.0")) // Sử dụng BOM cho Firebase
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Auth
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1") // Kiểm tra phiên bản mới nhất

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Cập nhật annotationProcessor cho Glide
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}