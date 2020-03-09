import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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
        implementation(Deps.kotlin)
        implementation(Deps.kotlin_common)
        implementation(Deps.ktor_client_core)
        implementation(Deps.ktor_client_json)
        implementation(Deps.ktor_client_serialization)
        implementation(Deps.kotlin_serialization)
    }

    sourceSets["commonTest"].dependencies {
        api("io.ktor:ktor-client-mock-jvm:${Versions.ktor_version}")
        api("io.ktor:ktor-client-mock-js:${Versions.ktor_version}")
        api("io.ktor:ktor-client-mock-native:${Versions.ktor_version}")
        api("io.ktor:ktor-client-mock:${Versions.ktor_version}")
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.12")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.kotlin_serialization_runtime)
        implementation(Deps.ktor_client_android)
        implementation(Deps.ktor_json_jvm)
        implementation(Deps.ktor_serialization_jvm)
        implementation(Deps.ktor_client_okhttp)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.kotlin_serialization_runtime_native)
        implementation(Deps.ktor_client_core_native)
        implementation(Deps.ktor_client_serialization_native)
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.serializer_version}")

        // HTTP
        implementation("io.ktor:ktor-client-ios:${Versions.ktor_version}")
        implementation("io.ktor:ktor-client-json-native:${Versions.ktor_version}")
        implementation("io.ktor:ktor-client-serialization-iosx64:${Versions.ktor_version}")
    }
}