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

        api("io.ktor:ktor-client-core:${Versions.ktor_version}")
        api("io.ktor:ktor-client-json:${Versions.ktor_version}")
        api("io.ktor:ktor-client-serialization:${Versions.ktor_version}")

        api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.serializer_version}")
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.kotlin)

        api("io.ktor:ktor-client-android:${Versions.ktor_version}")
        api("io.ktor:ktor-client-json-jvm:${Versions.ktor_version}")
        api("io.ktor:ktor-client-serialization-jvm:${Versions.ktor_version}")
        api("io.ktor:ktor-client-okhttp:${Versions.ktor_version}")
        api("io.ktor:ktor-client-serialization-jvm:${Versions.ktor_version}")
        
        api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serializer_version}")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.serializer_version}")

        implementation("io.ktor:ktor-client-ios:${Versions.ktor_version}")
        implementation("io.ktor:ktor-client-json-native:${Versions.ktor_version}")
        implementation("io.ktor:ktor-client-core-native:${Versions.ktor_version}")
        implementation("io.ktor:ktor-client-serialization-native:${Versions.ktor_version}")
    }
}