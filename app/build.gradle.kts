import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

android {
    namespace = "com.venfriti.inventorymanagement"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.venfriti.inventorymanagement"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            val localProperties = Properties()
            val localFile = rootProject.file("local.properties")

            if (localFile.exists()) {
                localProperties.load(FileInputStream(localFile))

                buildConfigField(
                    "String",
                    "APP_EMAIL",
                    "\"${localProperties.getProperty("APP_EMAIL")}\""
                )
                buildConfigField(
                    "String",
                    "APP_EMAIL_KEY",
                    "\"${localProperties.getProperty("APP_EMAIL_KEY")}\""
                )
                buildConfigField(
                    "String",
                    "ADMIN_EMAIL",
                    "\"${localProperties.getProperty("ADMIN_EMAIL")}\""
                )
                buildConfigField(
                    "String",
                    "EMAIL_HOST",
                    "\"${localProperties.getProperty("EMAIL_HOST")}\""
                )
                buildConfigField(
                    "String",
                    "EMAIL_PORT",
                    "\"${localProperties.getProperty("EMAIL_PORT")}\""
                )

            }
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
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/NOTICE.md"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.socket.io.client)
    implementation(libs.json)
    implementation(libs.java.websocket)
    implementation(libs.android.mail)
    implementation(libs.android.activation)
    implementation(libs.play.services.base)
    implementation(libs.androidx.material3.window.size.class1)



    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.play.services.basement)
    debugImplementation(libs.androidx.ui.tooling)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}