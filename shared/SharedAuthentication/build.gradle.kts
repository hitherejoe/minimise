import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("kotlinx-serialization")
}

repositories {
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

    cocoapods {
        summary = "Shared Authentication"
        homepage = "https://github.com/hitherejoe/minimise"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.common)
                implementation(Deps.Coroutines.coreCommon)
                implementation(project(":shared:SharedCommon"))
                implementation(project(":FirebaseAuthentication"))

                implementation(Deps.Kotlin.kotlinSerialization)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":FirebaseAuthentication"))
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(Deps.junit)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5")

                implementation(Deps.Ktor.clientMock)
                implementation(Deps.Ktor.clintMockJvm)
                implementation(Deps.Ktor.clientMockNative)

                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(Deps.junit)
                implementation(Deps.Coroutines.core)
                implementation(Deps.Coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Deps.Kotlin.stdLib)
                implementation(Deps.Coroutines.android)
                implementation(Deps.Coroutines.core)

                implementation(Deps.Kotlin.serializationRuntime)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Deps.Coroutines.native)

                implementation(Deps.Ktor.loggingIos)
                implementation(Deps.Kotlin.serializationRuntimeNative)
            }
        }
    }
}