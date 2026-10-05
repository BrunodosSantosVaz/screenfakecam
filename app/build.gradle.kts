import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
}

// version.properties is the single source of the version (bigbang.toml entrega.arquivo_versao).
val appVersion: String =
    Properties()
        .apply { rootProject.file("version.properties").inputStream().use { load(it) } }
        .getProperty("version")
val (major, minor, patch) = appVersion.split(".").map(String::toInt)

// Release signing comes only from the environment (packaging/android/build.sh, CI secrets): never from the repository.
val keystoreFile: String? = System.getenv("BB_KEYSTORE_FILE")

android {
    namespace = "io.github.brunodossantosvaz.screenfakecam"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.brunodossantosvaz.screenfakecam"
        minSdk = 26
        targetSdk = 36
        versionCode = major * 10_000 + minor * 100 + patch
        versionName = appVersion
    }

    signingConfigs {
        if (keystoreFile != null) {
            create("release") {
                storeFile = file(keystoreFile)
                storePassword = System.getenv("BB_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("BB_KEY_ALIAS")
                keyPassword = System.getenv("BB_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (keystoreFile != null) signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }

    sourceSets {
        // Acceptance tests live in tests/aceite/ (Big Bang lock), compiled and run with the unit tests.
        getByName("test").kotlin.directories.add("../tests/aceite")
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    lint {
        warningsAsErrors = false
        abortOnError = true
    }
}

tasks.withType<Test>().configureEach {
    // Robolectric reads FileDescriptor internals, closed by default on JDK 17+.
    jvmArgs("--add-opens=java.base/java.io=ALL-UNNAMED")
}

kover {
    reports {
        filters {
            includes {
                packages(
                    "io.github.brunodossantosvaz.screenfakecam.domain",
                    "io.github.brunodossantosvaz.screenfakecam.application",
                )
            }
        }
        verify {
            rule {
                minBound(80)
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.exifinterface)
    implementation(libs.zxing.core)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.konsist)
}
