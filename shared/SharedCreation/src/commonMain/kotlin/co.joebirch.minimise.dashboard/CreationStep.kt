package co.joebirch.minimise.dashboard

enum class CreationStep(val position: Int) {
    NAME(0), TYPE(1), CATEGORY(2), FREQUENCY(3), POSITIVE(4),
    NEGATIVE(5), REMIND(6), FINISHED(7);

    companion object {
        fun fromPosition(position: Int): CreationStep {
            return when (position) {
                0 -> NAME
                1 -> TYPE
                2 -> CATEGORY
                3 -> FREQUENCY
                4 -> POSITIVE
                5 -> NEGATIVE
                6 -> REMIND
                7 -> FINISHED
                else -> throw IllegalArgumentException(
                    "That position does not translate to a supported step"
                )
            }
        }
    }
}