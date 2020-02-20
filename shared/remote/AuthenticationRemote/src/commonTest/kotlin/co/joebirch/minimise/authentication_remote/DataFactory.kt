package co.joebirch.minimise.authentication_remote

object DataFactory {

    fun randomString(): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun randomInt() = (1..500).shuffled().first()
}