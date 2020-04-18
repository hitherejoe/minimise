package co.joebirch.minimise.shared_common

import com.russhwolf.settings.ExperimentalListener
import com.russhwolf.settings.ObservableSettings
import kotlin.properties.ReadWriteProperty

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import com.russhwolf.settings.contains
import com.russhwolf.settings.minusAssign
import com.russhwolf.settings.nullableString

class SettingsRepository(private val settings: Settings) {

    val supportedSettings: List<SettingConfig<*>> = listOf(
        NullableStringSettingConfig(settings, "KEY_AUTH_TOKEN")
    )

    fun clear() = settings.clear()
}

sealed class SettingConfig<T>(
    private val settings: Settings,
    val key: String,
    defaultValue: T,
    delegate: Settings.(String, T) -> ReadWriteProperty<Any?, T>,
    private val toType: String.() -> T
) {
    private var value: T by settings.delegate(key, defaultValue)

    @ExperimentalListener
    private var listener: SettingsListener? = null

    fun remove() {
        settings -= key
    }

    fun exists(): Boolean = key in settings
    fun get(): String = value.toString()
    fun set(value: String): Boolean {
        return try {
            this.value = value.toType()
            true
        } catch (exception: Exception) {
            false
        }
    }

    @ExperimentalListener
    var isLoggingEnabled: Boolean
        get() = listener != null
        set(value) {
            val settings = settings as? ObservableSettings ?: return
            listener = if (value) {
                settings.addListener(key) { println("$key = ${get()}") }
            } else {
                listener?.deactivate()
                null
            }
        }

    override fun toString() = key
}

sealed class NullableSettingConfig<T : Any>(
    settings: Settings,
    key: String,
    delegate: Settings.(String) -> ReadWriteProperty<Any?, T?>,
    toType: String.() -> T
) : SettingConfig<T?>(settings, key, null, { it, _ -> delegate(it) }, toType)

class NullableStringSettingConfig(settings: Settings, key: String) :
    NullableSettingConfig<String>(settings, key, Settings::nullableString, { this })