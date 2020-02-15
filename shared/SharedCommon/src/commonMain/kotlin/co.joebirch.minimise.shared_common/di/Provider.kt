package co.joebirch.minimise.shared_common.di

abstract class Provider<T> {

    private val original by lazy { create() }
    var override: T? = null

    abstract fun create(): T

    fun get(): T = override ?: original
    fun lazyGet(): Lazy<T> = lazy { get() }
}