import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val ideaActive = System.getProperty("idea.active") == "true"

plugins {
    id("org.jetbrains.kotlin.native.cocoapods")
    kotlin("multiplatform")
}


kotlin {
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework("Authentication") {
                baseName = "Authentication"
            }
        }
    }

    jvm("android")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.kotlin_common)
                implementation(Deps.coroutines_core)
                implementation(Deps.coroutines_core_common)
                implementation(project(":shared:SharedCommon"))

                implementation("co.touchlab:koin-core:3.0.2-khan")
                implementation("co.touchlab:stately:0.9.6")
                implementation("co.touchlab:stately-collections:0.9.6")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.kotlin)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Deps.kotlin)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3")
                implementation("co.touchlab:koin-core:3.0.2-khan")
            }
        }
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