object Versions {
    val minSdk = 21
    val targetSdk = 29
    val compileSdk = 29
    val kotlin = "1.3.61"
    val compose = "0.1.0-dev06"
    val coroutine_version = "1.3.3"
    val serializer_version = "0.14.0"
    val ktor_version = "1.3.1"

    val material_version = "1.2.0-alpha03"
    val dagger = "2.25.4"
}

object Deps {
    val coroutines_core_common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutine_version}"
    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_version}"
    val coroutines_native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutine_version}"

    val compose_tooling = "androidx.ui:ui-tooling:${Versions.compose}"
    val compose_layout = "androidx.ui:ui-layout:${Versions.compose}"
    val compose_material = "androidx.ui:ui-material:${Versions.compose}"
    val compose_core = "androidx.ui:ui-core:${Versions.compose}"
    val compose_framework = "androidx.ui:ui-framework:${Versions.compose}"
    val compose_android = "androidx.ui:ui-android:${Versions.compose}"
    val compose_text = "androidx.ui:ui-text:${Versions.compose}"
    val compose_foundation = "androidx.ui:ui-foundation:${Versions.compose}"
    val compose_runtime = "androidx.compose:compose-runtime:${Versions.compose}"


    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlin_extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    val kotlin_common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    val ktor_client_core = "io.ktor:ktor-client-core:${Versions.ktor_version}"
    val ktor_client_json = "io.ktor:ktor-client-json:${Versions.ktor_version}"
    val ktor_client_serialization = "io.ktor:ktor-client-serialization:${Versions.ktor_version}"
    val kotlin_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.serializer_version}"

    val ktor_client_android = "io.ktor:ktor-client-android:${Versions.ktor_version}"
    val ktor_json_jvm = "io.ktor:ktor-client-json-jvm:${Versions.ktor_version}"
    val ktor_serialization_jvm = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor_version}"
    val ktor_client_okhttp = "io.ktor:ktor-client-okhttp:${Versions.ktor_version}"
    val kotlin_serialization_runtime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serializer_version}"

    val ktor_client_json_nativektor_client_ios = "io.ktor:ktor-client-ios:${Versions.ktor_version}"
    val ktor_client_json_native = "io.ktor:ktor-client-json-native:${Versions.ktor_version}"
    val ktor_client_core_native = "io.ktor:ktor-client-core-native:${Versions.ktor_version}"
    val ktor_client_serialization_native = "io.ktor:ktor-client-serialization-native:${Versions.ktor_version}"

    val kotlin_serialization_runtime_native = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.serializer_version}"

    val material = "com.google.android.material:material:${Versions.material_version}"


}

object AndroidDeps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val dagger_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
}