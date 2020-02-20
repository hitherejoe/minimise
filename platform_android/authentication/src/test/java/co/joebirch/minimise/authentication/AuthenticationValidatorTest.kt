package co.joebirch.minimise.authentication

import co.joebirch.minimise.authentication.util.AuthenticationValidator
import org.junit.Test

import org.junit.Assert.*

class AuthenticationValidatorTest {

    private val authenticationValidator =
        AuthenticationValidator()

    @Test
    fun `Input data returns as valid`() {
        assertTrue(authenticationValidator.validateInputData("password",
            "someemail@email.com"))
    }

    @Test
    fun `Input data returns as invalid when password empty`() {
        assertFalse(authenticationValidator.validateInputData("",
            "someemail@email.com"))
    }

    @Test
    fun `Input data returns as invalid when email empty`() {
        assertFalse(authenticationValidator.validateInputData("password", ""))
    }

    @Test
    fun `Input data returns as invalid when email and password empty`() {
        assertFalse(authenticationValidator.validateInputData("", ""))
    }
}
