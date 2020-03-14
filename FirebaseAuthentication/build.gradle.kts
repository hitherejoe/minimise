import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

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

    sourceSets["commonMain"].dependencies {
        implementation(project(":shared:SharedCommon"))

        implementation(Deps.Kotlin.common)
        implementation(Deps.Kotlin.kotlinSerialization)
        implementation(Deps.Kotlin.stdLib)

        implementation(Deps.Ktor.clientCore)
        implementation(Deps.Ktor.clientJson)
        implementation(Deps.Ktor.clientSerialization)
    }

    sourceSets["commonTest"].dependencies {
        api(Deps.Ktor.clientMock)
        api(Deps.Ktor.clintMockJvm)
        api(Deps.Ktor.clientMockNative)

        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.12")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.Kotlin.serializationRuntime)
        implementation(Deps.Ktor.clientAndroid)
        implementation(Deps.Ktor.clientJsonJvm)
        implementation(Deps.Ktor.clientSerializationJvm)
        implementation(Deps.Ktor.clientOkhttp)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.Kotlin.serializationRuntimeNative)
        implementation(Deps.Ktor.clientIos)
        implementation(Deps.Ktor.clientSerializationNative)
        implementation(Deps.Kotlin.serializationRuntimeNative)

        // HTTP
        implementation(Deps.Ktor.clientJsonNative)
        implementation(Deps.Ktor.clientSerializationNativeX64)
    }
}