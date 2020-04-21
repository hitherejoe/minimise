import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "AuthenticationRemote"
            }
        }
    }

    jvm("android")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.common)
                implementation(Deps.Kotlin.kotlinSerialization)
                implementation(Deps.Kotlin.stdLib)

                implementation(Deps.Ktor.clientCore)
                implementation(Deps.Ktor.clientJson)
                implementation(Deps.Ktor.clientSerialization)
                implementation(Deps.Ktor.logging)
            }
        }

        val commonTest by getting {
            dependencies {
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
                implementation(Deps.Kotlin.serializationRuntime)
                implementation(Deps.Ktor.clientAndroid)
                implementation(Deps.Ktor.clientJsonJvm)
                implementation(Deps.Ktor.clientSerializationJvm)
                implementation(Deps.Ktor.clientOkhttp)
                implementation(Deps.Ktor.loggingAndroid)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Deps.Coroutines.native) {
                    version {
                        strictly("1.3.5-native-mt")
                    }
                }
                implementation(Deps.Ktor.clientIos)
                implementation(Deps.Ktor.clientSerializationNative)
                implementation(Deps.Kotlin.serializationRuntimeNative)
                implementation(Deps.Ktor.clientCoreNative)
                implementation(Deps.Ktor.clientJsonNative)
                implementation(Deps.Ktor.loggingIos)
            }
        }
    }
}