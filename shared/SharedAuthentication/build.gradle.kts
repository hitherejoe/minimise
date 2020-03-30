import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
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

    iOSTarget("ios") {
        binaries {
            framework("SharedAuthentication") {
                baseName = "SharedAuthentication"
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
            }
        }

        val mobileMain by creating {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn(mobileMain)
            dependencies {
                implementation(Deps.Kotlin.stdLib)
                implementation(Deps.Coroutines.android)
                implementation(Deps.Coroutines.core)
            }
        }

        val iosMain by getting {
            dependsOn(mobileMain)
            dependencies {
                implementation(Deps.Kotlin.stdLib)
                implementation(Deps.Coroutines.native)
            }
        }
    }
}