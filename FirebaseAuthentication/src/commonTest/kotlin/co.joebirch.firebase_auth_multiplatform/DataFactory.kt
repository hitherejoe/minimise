package co.joebirch.firebase_auth_multiplatform

import kotlin.random.Random.Default.nextBoolean

object DataFactory {

    fun randomString(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun randomInt() = (1..500).shuffled().first()

    fun randomLong() = randomInt().toLong()

    fun randomBoolean() = nextBoolean()
}