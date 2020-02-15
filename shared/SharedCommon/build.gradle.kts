import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.native.cocoapods")
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
            framework("SharedCommon") {
                baseName = "SharedCommon"
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation(Deps.kotlin_common)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutine_version}")
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.kotlin)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine_version}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_version}")
    }

    sourceSets["iosMain"].dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutine_version}")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:${Versions.coroutine_version}")
    }
}


val packForXcode by tasks.creating(Sync::class) {
    group = "build"

    //selecting the right configuration for the iOS framework depending on the Xcode environment variables
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)

    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)
