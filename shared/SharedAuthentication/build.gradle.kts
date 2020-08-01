import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

plugins {
    id("com.apollographql.apollo")
    kotlin("multiplatform")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

version = "1.0"
group = "co.joebirch.minimise.authentication"

kotlin {
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64
    jvm("android")
    val buildForDevice = project.findProperty("device") as? Boolean ?: false
    iOSTarget("ios") {
        binaries {
            framework("SharedAuthentication") {
                baseName = "SharedAuthentication"
                //if (!buildForDevice) embedBitcode("disable")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.common)
                implementation(Deps.Coroutines.core)
                implementation(Deps.Apollo.api)
                implementation(Deps.Apollo.runtime)
                implementation(project(":shared:SharedCommon"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(Deps.junit)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8-1.4.0-rc")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8-1.4.0-rc")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Deps.Apollo.api)
                implementation(Deps.Kotlin.stdLib)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Deps.Apollo.api)
                implementation(Deps.Kotlin.stdLib)
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}