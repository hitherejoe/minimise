package co.joebirch.minimise.dashboard

enum class CreationStep(val position: Int) {
    NAME(0), CATEGORY(1), FREQUENCY(2), POSITIVE(3),
    NEGATIVE(4), REMIND(5), FINISHED(6);

    companion object {
        fun fromPosition(position: Int): CreationStep {
            return when (position) {
                0 -> NAME
                1 -> CATEGORY
                2 -> FREQUENCY
                3 -> POSITIVE
                4 -> NEGATIVE
                5 -> REMIND
                6 -> FINISHED
                else -> throw IllegalArgumentException(
                    "That position does not translate to a supported step"
                )
            }
        }
    }
}