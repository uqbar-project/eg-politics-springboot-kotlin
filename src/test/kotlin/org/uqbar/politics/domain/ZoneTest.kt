package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class ZoneTest {

    @Test
    fun `zone without name fails validation`() {
        assertThrows<UserException> { Zone().apply { name = "  " }.validate() }
    }

    @Test
    fun `zone without candidates fails validation`() {
        assertThrows<UserException> { Zone().apply {
            name = "Zone"
            candidates = mutableSetOf()
        }.validate() }
    }

    @Test
    fun `zone initially has no id`() {
        assertNull(zoneOk().id)
    }

    @Test
    fun `zone toString returns name`() {
        val zone = zoneOk()
        Assertions.assertEquals(zone.name, zone.toString())
    }
}

fun zoneOk() = Zone().apply {
    name = "CABA"
    candidates = mutableSetOf(Candidate().apply {
        name = "Juan Carlos"
        party = PeronistParty().apply {
            name = "FREJULI"
            members = 12200
        }
    })
}