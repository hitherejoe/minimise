package co.joebirch.minimise.dashboard

class DashboardState(
    val selectedCategory: Category = Category.PendingBelongings,
    val pendingBelongings: List<Belonging> = emptyList()
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: DashboardState) {
        var category = uiModel.selectedCategory
        var pendingBelongings = uiModel.pendingBelongings

        fun build(): DashboardState {
            return DashboardState(category, pendingBelongings)
        }
    }
}