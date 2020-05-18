package co.joebirch.minimise.dashboard

class CreationState(
    val currentStep: CreationStep,
    val isLoading: Boolean = false,
    val name: String = "",
    val store: String = "",
    val frequencyCount: Float = 0f
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: CreationState) {
        private val currentState = uiModel
        var creationStep = currentState.currentStep
        var isLoading = currentState.isLoading
        var name = currentState.name
        var store = currentState.store
        var frequency = currentState.frequencyCount

        fun build() = CreationState(creationStep, isLoading, name, store, frequency)
    }
}