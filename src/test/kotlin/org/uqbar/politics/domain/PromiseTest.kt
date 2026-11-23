package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

class PromiseTest {

    @Test
    fun `promise with blank action fails validation`() {
        assertThrows<UserException> { Promise().validate() }
        assertThrows<UserException> { Promise(action = "").validate() }
        assertThrows<UserException> { Promise(action = "   ").validate() }
    }

    @Test
    fun `valid promise passes validation`() {
        Promise(action = "Terminar con xxx").validate()
    }

    @Test
    fun `una promise no tiene inicialmente identificador y toma la date del dia`() {
        val promise = Promise(action = "Terminar con xxx")
        assertNull(promise.id)
        Assertions.assertEquals(LocalDate.now(), promise.date)
    }
}