plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
    
    
}

android {
    namespace = "com.kyant.backdrop"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters"
        )
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.debaxyz.shapes)
}
