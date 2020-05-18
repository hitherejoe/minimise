package co.joebirch.minimise.dashboard

enum class CreationStep(val position: Int) {
    NAME(0), STORE(1), FREQUENCY(2);

    companion object {
        fun fromPosition(position: Int): CreationStep {
            return when (position) {
                0 -> NAME
                1 -> STORE
                2 -> FREQUENCY
                else -> throw IllegalArgumentException(
                    "That position does not translate to a supported step"
                )
            }
        }
    }
}