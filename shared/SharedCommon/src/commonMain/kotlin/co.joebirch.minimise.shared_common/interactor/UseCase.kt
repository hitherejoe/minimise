package co.joebirch.minimise.shared_common.interactor

abstract class UseCase<T, Params> {

    abstract fun run(params: Params, completion: (T) -> Unit)

    abstract fun runOnNative(params: Params, completion: (T) -> Unit)

}