object Versions {
    val minSdk = 21
    val targetSdk = 29
    val compileSdk = 29
    val kotlin = "1.3.61"

    val coroutine_version = "1.3.3"
    val serializer_version = "0.14.0"
    val ktor_version = "1.3.0"

    val material_version = "1.2.0-alpha03"
}

object Deps {
    val coroutines_core_common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutine_version}"
    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_version}"
    val coroutines_native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutine_version}"

    val kotlin_android = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlin_common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"

    val material = "com.google.android.material:material:${Versions.material_version}"
}