
buildscript {

    extra["kotlin_version"] = Versions.kotlin
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/joebirch/joebirch")
        maven(url = "https://dl.bintray.com/florent37/maven")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.45")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://dl.bintray.com/kotlin/kotlinx.html")
        maven(url = "https://dl.bintray.com/kotlin/ktor")
        maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")
        maven(url = "https://kotlin.bintray.com/kotlin-dev")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://ci.android.com/builds/submitted/6116482/androidx_snapshot/latest/repository/")
        maven(url = "https://dl.bintray.com/joebirch/joebirch")
        maven(url = "https://dl.bintray.com/florent37/maven")
    }

    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            preferProjectModules()

            eachDependency {
                if (requested.name.startsWith("kotlin-stdlib")) {
                    useVersion(Versions.kotlin)
                }
                if (requested.name.startsWith("org.jetbrains.kotlin.multiplatform")) {
                    useVersion(Versions.kotlin)
                }
            }
        }
    }
}

tasks.register("clean").configure {
    delete("build")
}
