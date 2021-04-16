package co.joebirch.minimise.dashboard

class CreationState(
    val currentStep: CreationStep = CreationStep.NAME,
    val isLoading: Boolean = false,
    val name: String = "",
    val categories: List<String> = emptyList(),
    val frequencyCount: Float = 0f,
    val positiveReasons: List<String> = emptyList(),
    val negativeReasons: List<String> = emptyList(),
    val daysToRemind: Int = 2,
    val itemType: ItemType? = null
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: CreationState) {
        private val currentState = uiModel
        var creationStep = currentState.currentStep
        var isLoading = currentState.isLoading
        var name = currentState.name
        var categories = currentState.categories
        var frequency = currentState.frequencyCount
        var positiveReasons = currentState.positiveReasons
        var negativeReasons = currentState.negativeReasons
        var daysToRemind = currentState.daysToRemind
        var itemType = currentState.itemType

        fun build() = CreationState(
            creationStep, isLoading, name, categories,
            frequency, positiveReasons, negativeReasons, daysToRemind,
            itemType
        )
    }
}