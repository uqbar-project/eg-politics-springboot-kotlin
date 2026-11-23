package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class PeronistPartyTest {

    @Test
    fun `peronist party without name fails validation`() {
        assertThrows<UserException> { PeronistParty().apply { name = "  " }.validate() }
    }

    @Test
    fun `peronist party without enough members fails validation`() {
        assertThrows<UserException> { PeronistParty().apply { name = "FREJUPO" }.validate() }
    }

    @Test
    fun `valid peronist party passes validation`() {
        peronistPartyOk().validate()
    }

    @Test
    fun `peronist party initially has no id`() {
        assertNull(peronistPartyOk().id)
    }

    @Test
    fun `peronist party toString returns name`() {
        val party = peronistPartyOk()
        Assertions.assertEquals(party.name, party.toString())
    }
}

fun peronistPartyOk() = PeronistParty().apply {
    name = "FREJUPO"
    members = 10000
    populist = true
}
