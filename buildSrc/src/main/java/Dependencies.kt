package co.joebirch.minimise.buildsrc

object Versions {
    val minSdk = 21
    val targetSdk = 29
    val compileSdk = 29
    val kotlin = Deps.Kotlin.version
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.0-alpha05"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.70"

    const val junit = "junit:junit:4.12"

    object Kotlin {
        const val version = "1.3.70"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:$version"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"

        const val serializationRuntimeNative =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.20.0"

        const val serializationRuntime =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0"

        const val kotlinSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.20.0"
    }

    object Lifecycle {
        const val lifecycleVersion = "2.2.0"

        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
        const val common = "androidx.lifecycle:lifecycle-common:$lifecycleVersion"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
        const val runtime = "androidx.lifecycle:lifecycle-runtime:$lifecycleVersion"
    }

    object AndroidX {
        const val annotation = "androidx.annotation:annotation:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0"
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"

        const val activity = "androidx.activity:activity:1.2.0-alpha02"
        const val activityKtx = "androidx.activity:activity-ktx:1.2.0-alpha02"

        const val junit = "androidx.test.ext:junit:1.1.1"
    }

    object Navigation {
        const val version = "2.3.0-alpha03"
        const val fragment = "androidx.navigation:navigation-fragment:$version"
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui:$version"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Espresso {
        const val version = "3.2.0"
        const val core = "androidx.test.espresso:espresso-core:$version"
    }

    object Mockito {
        const val version = "3.2.0"
        const val core = "androidx.test.espresso:espresso-core:$version"
        const val android = "org.mockito:mockito-android:$version"
    }

    object Fragment {
        const val version = "1.2.2"

        const val base = "androidx.fragment:fragment:1.3.0-alpha02"
        const val ktx = "androidx.fragment:fragment-ktx:$version"
        const val testing = "androidx.fragment:fragment-testing:$version"
    }

    object Coroutines {
        const val version = "1.3.5"
        const val coreCommon =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$version"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val native =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$version"
        const val iOS64 =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:$version"
        const val iOSarm64 =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:$version"
    }

    object Compose {
        const val version = "0.1.0-dev08"

        const val android = "androidx.ui:ui-android:$version"
        const val core = "androidx.ui:ui-core:$version"
        const val foundation = "androidx.ui:ui-foundation:$version"
        const val framework = "androidx.ui:ui-framework:$version"
        const val layout = "androidx.ui:ui-layout:$version"
        const val material = "androidx.ui:ui-material:$version"
        const val runtime = "androidx.compose:compose-runtime:$version"
        const val text = "androidx.ui:ui-text:$version"
        const val tooling = "androidx.ui:ui-tooling:$version"
    }

    object Ktor {
        const val version = "1.3.2"

        const val clientCore = "io.ktor:ktor-client-core:$version"
        const val clientJson = "io.ktor:ktor-client-json:$version"
        const val clientSerialization = "io.ktor:ktor-client-serialization:$version"

        const val clientAndroid = "io.ktor:ktor-client-android:$version"
        const val clientJsonJvm = "io.ktor:ktor-client-json-jvm:$version"
        const val clientSerializationJvm = "io.ktor:ktor-client-serialization-jvm:$version"
        const val clientOkhttp = "io.ktor:ktor-client-okhttp:$version"


        const val clientIos = "io.ktor:ktor-client-ios:$version"
        const val clientJsonNative = "io.ktor:ktor-client-json-native:$version"
        const val clientCoreNative = "io.ktor:ktor-client-core-native:$version"
        const val clientSerializationNative =
            "io.ktor:ktor-client-serialization-native:$version"
       // const val clientSerializationNativeX64 =
       //     "io.ktor:ktor-client-serialization-iosx64:$version"

        const val clintMockJvm = "io.ktor:ktor-client-mock-jvm:$version"
        const val clientMockJs = "io.ktor:ktor-client-mock-js:$version"
        const val clientMockNative = "io.ktor:ktor-client-mock-native:$version"
        const val clientMock = "io.ktor:ktor-client-mock:$version"
    }

    object Google {
        val material = "com.google.android.material:material:1.2.0-alpha03"
    }

    object Dagger {
        private const val version = "2.25.4"

        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val processor = "com.google.dagger:dagger-android-processor:$version"
    }

}