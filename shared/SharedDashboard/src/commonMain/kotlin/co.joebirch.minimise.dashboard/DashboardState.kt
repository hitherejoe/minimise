package co.joebirch.minimise.dashboard

sealed class DashboardState(
    val selectedCategory: Category = Category.PendingBelongings
) {

    data class Idle(
        val category: Category = Category.PendingBelongings
    ) : DashboardState(category)

    data class Loading(
        val category: Category = Category.PendingBelongings
    ) : DashboardState(category)

    object Success : DashboardState()

    data class Error(
        val category: Category = Category.PendingBelongings
    ) : DashboardState(category)

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: DashboardState) {
        var category = uiModel.selectedCategory

        fun build(): DashboardState {
            return Idle(category)
        }
    }
}