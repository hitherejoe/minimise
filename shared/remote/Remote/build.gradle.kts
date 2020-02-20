import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "Remote"
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation(Deps.kotlin)

        api(Deps.ktor_client_core)
        api(Deps.ktor_client_json)
        api(Deps.ktor_client_serialization)

        api(Deps.kotlin_serialization)
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.kotlin)
        api(Deps.kotlin_serialization_runtime)

        api(Deps.ktor_client_android)
        api(Deps.ktor_json_jvm)
        api(Deps.ktor_serialization_jvm)
        api(Deps.ktor_client_okhttp)
    }

    sourceSets["iosMain"].dependencies {
        api(Deps.kotlin_serialization_runtime_native)

        api(Deps.ktor_client_ios)
        api(Deps.ktor_client_json_native)
        api(Deps.ktor_client_core_native)
        api(Deps.ktor_client_serialization_native)
    }
}