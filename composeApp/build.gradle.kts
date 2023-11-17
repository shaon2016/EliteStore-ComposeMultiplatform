import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)

    id("com.squareup.sqldelight").version("1.5.5")
}

group = "com.ashiq.elitestore"
version = "1.0"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation("com.squareup.sqldelight:android-driver:1.5.5")
        }

        iosMain.dependencies {
            implementation("com.squareup.sqldelight:native-driver:1.5.5")
        }

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            api(compose.components.resources)

            // image loading // Note 1.7.0 doesnt load image on android
            api("io.github.qdsfdhvh:image-loader:1.6.8")

            // DI
            api(libs.koin.core)
            api(libs.koin.compose)

            // Networking
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.contentNegotiation)

            // Coroutine
            api(libs.coroutine.core)

            // Log
            api(libs.napier)

            // Coroutine
            implementation(libs.coroutine.core)

            // Navigation
            api(libs.precompose)
            api(libs.precompose.viewModel)
            api(libs.precompose.koin)

            // database
            implementation("com.squareup.sqldelight:runtime:1.5.5")
        }

    }
}

android {
    namespace = "com.ashiq.elitestore"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.ashiq.elitestore"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.ashiq.elitestore.data.local"
    }
}
