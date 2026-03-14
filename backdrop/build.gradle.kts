plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
    
    id("maven-publish")
    id("signing")
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
// Configure publishing
publishing {
    publications {
        create<MavenPublication>("release") {
            // Maven coordinates: groupId:artifactId:version
            groupId = "io.github.debaxyz"           // Your group ID
            artifactId = "backdrop"                  // Your artifact ID
            version = "1.0.0"                         // Library version

            afterEvaluate {
                from(components["release"])
            }
            
            pom {
               name.set("Backdrop")
               description.set("Android Compose Liquid Glass effect.")
               inceptionYear.set("2026")
               url.set("https://github.com/debaxyz/AndroidLiquidGlass/")
           licenses {
           license {
              name.set("The Apache License, Version 2.0")
              url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
              distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
           developers {
           developer {
             id.set("debaxyz")
             name.set("Deba")
             url.set("https://github.com/AndroidLiquidGlass/")
      }
    }
    scm {
      url.set("https://github.com/debaxyz/debaxyz/AndroidLiquidGlass/")
      connection.set("scm:git:git://github.com/debaxyz/AndroidLiquidGlass.git")
      developerConnection.set("scm:git:ssh://git@github.com/debaxyz/AndroidLiquidGlass.git")
      }
    }
  } 
}

    repositories {
    
        maven {
            name = "GitHubPackages"
            // Format: https://maven.pkg.github.com/OWNER/REPOSITORY
            url = uri("https://maven.pkg.github.com/debaxyz/AndroidLiquidGlass")
            
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        
        maven {
            val releasesRepoUrl = layout.buildDirectory.dir("repos/releases")
            val snapshotsRepoUrl = layout.buildDirectory.dir("repos/snapshots")
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        }
    }
}

// Signing configuration - moved after publishing and uses lazy evaluation
signing {
    sign(publishing.publications["release"])
}
tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}