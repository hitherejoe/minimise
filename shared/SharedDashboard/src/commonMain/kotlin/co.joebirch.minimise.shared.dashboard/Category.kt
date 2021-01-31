package co.joebirch.minimise.shared.dashboard

sealed class Category(val title: String, val key: String) {

    object PendingBelongings : Category("Pending", "pending_belongings")

    object Belongings : Category("Belongings", "owned_belongings")

}