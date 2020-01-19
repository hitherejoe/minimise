
buildscript {

    extra["kotlin_version"] = "1.3.61"
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha08")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
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
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://kotlin.bintray.com/kotlin-dev")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://ci.android.com/builds/submitted/6116482/androidx_snapshot/latest/repository/")
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