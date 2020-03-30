import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.native.cocoapods")
}

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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.common)
                implementation(Deps.Coroutines.coreCommon)
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
                implementation(Deps.Coroutines.native)
                //api(Deps.Coroutines.iOS64)
                //api(Deps.Coroutines.iOSarm64)
            }
        }
    }
}