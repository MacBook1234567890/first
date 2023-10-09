plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.first"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.first"
        minSdk = 23
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
    buildFeatures{
        viewBinding =true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("org.jetbrains:annotations:15.0")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-firestore:24.8.1")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
//    implementation ("com.google.firebase:firebase-core:22.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.databinding:databinding-common:8.1.2")
    implementation ("com.google.firebase:firebase-messaging:23.2.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation ("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation ("androidx.browser:browser:1.6.0")
    implementation ("com.sun.mail:javax.mail:1.6.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation ("org.jitsi.react:jitsi-meet-sdk:7.0.1")//1:05
    implementation ("org.jitsi.react:jitsi-meet-sdk:")





}