import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import co.joebirch.minimise.buildsrc.Deps
import co.joebirch.minimise.buildsrc.Versions

plugins {
    id("org.jetbrains.kotlin.native.cocoapods")
    kotlin("multiplatform")
}

repositories {
    google()
    jcenter()
    mavenCentral()
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

    sourceSets["commonMain"].dependencies {
        implementation(Deps.Kotlin.common)
        implementation(Deps.Coroutines.core)
        implementation(project(":shared:SharedCommon"))
//        implementation(project(":FirebaseAuthentication"))
    }

    sourceSets["commonTest"].dependencies {
      //  implementation(project(":FirebaseAuthentication"))
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
        implementation(Deps.junit)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")
    }

    sourceSets["androidMain"].dependencies {
        implementation(Deps.Kotlin.stdLib)
        implementation(Deps.Coroutines.android)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.Kotlin.stdLib)
        implementation(Deps.Coroutines.native)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)
