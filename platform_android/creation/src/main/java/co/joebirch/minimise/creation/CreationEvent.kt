package co.joebirch.minimise.creation

import co.joebirch.minimise.dashboard.ItemType

sealed class CreationEvent {

    data class NameChanged(val name: String) : CreationEvent()
    data class ItemTypeChanged(val itemType: ItemType) : CreationEvent()
    data class CategoriesChanged(val categories: List<String>) : CreationEvent()
    data class FrequencyChanged(val frequency: Float) : CreationEvent()
    data class ReminderLengthChanged(val days: Int) : CreationEvent()
    data class PositiveNotesChanged(val notes: List<String>) : CreationEvent()
    data class NegativeNotesChanged(val notes: List<String>) : CreationEvent()
    object NextStepRequested : CreationEvent()
    object PreviousStepRequested : CreationEvent()
    object FormCompleted : CreationEvent()

}