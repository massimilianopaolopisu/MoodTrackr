import java.util.Base64
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.moodtrackr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moodtrackr"
        minSdk = 26
        targetSdk = 34
        versionCode = 11
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val isGitHubActions = System.getenv("GITHUB_ACTIONS") == "true"

            if (isGitHubActions) {
                val base64Keystore = System.getenv("RELEASE_KEYSTORE")
                val storeFilePath = rootProject.file("release.keystore")
                storeFilePath.writeBytes(Base64.getDecoder().decode(base64Keystore))

                storeFile = storeFilePath
                storePassword = System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            } else {
                val keystoreProperties = Properties()
                val keystorePropertiesFile = rootProject.file("keystore.properties")
                keystoreProperties.load(keystorePropertiesFile.inputStream())

                storeFile = file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.ui:ui-graphics:1.5.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material3:material3:1.2.0-alpha07")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.2")
    implementation("androidx.navigation:navigation-compose:2.7.2")
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("com.google.dagger:dagger:2.44")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.anggrayudi:storage:1.5.5")
    kapt("com.google.dagger:hilt-compiler:2.44")
    kapt("com.google.dagger:dagger-compiler:2.44")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.1")
    implementation(kotlin("reflect"))
}

kapt {
    correctErrorTypes = true
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}