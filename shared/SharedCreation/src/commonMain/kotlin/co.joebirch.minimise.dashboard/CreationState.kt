package co.joebirch.minimise.dashboard

class CreationState(
    val currentStep: CreationStep,
    val isLoading: Boolean = false
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: CreationState) {
        private val currentState = uiModel
        var creationStep = currentState.currentStep
        var isLoading = currentState.isLoading

        fun build() = CreationState(creationStep, isLoading)
    }
}