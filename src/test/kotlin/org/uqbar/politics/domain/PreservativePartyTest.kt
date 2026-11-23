package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

class PreservativePartyTest {

    @Test
    fun `preservative party without name fails validation`() {
        assertThrows<UserException> { PreservativeParty().apply { name = "  " }.validate() }
    }

    @Test
    fun `preservative party without enough members fails validation`() {
        assertThrows<UserException> { PreservativeParty().apply { name = "Elfo" }.validate() }
    }

    @Test
    fun `valid preservative party passes validation`() {
        preservativePartyOk().validate()
    }

    @Test
    fun `preservative party initially has no id`() {
        Assertions.assertNull(preservativePartyOk().id)
    }

    @Test
    fun `preservative party toString returns name`() {
        val party = preservativePartyOk()
        Assertions.assertEquals(party.name, party.toString())
    }
}

fun preservativePartyOk() = PreservativeParty().apply {
    name = "Elfo"
    members = 10000
    foundationDate = LocalDate.now()
}