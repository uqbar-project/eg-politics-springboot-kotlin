package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class ZonaTest {

    @Test
    fun `una zona sin descripcion no pasa la validacion`() {
        assertThrows<UserException> { Zona().apply { descripcion = "  " }.validar() }
    }

    @Test
    fun `una zona sin candidates no pasa la validacion`() {
        assertThrows<UserException> { Zona().apply {
            descripcion = "Zona"
            candidates = mutableSetOf()
        }.validar() }
    }

    @Test
    fun `una zona inicialmente no tiene identificador`() {
        assertNull(zonaOk().id)
    }

    @Test
    fun `el toString de la zona esta basado en la descripcion`() {
        val zona = zonaOk()
        Assertions.assertEquals(zona.descripcion, zona.toString())
    }
}

fun zonaOk() = Zona().apply {
    descripcion = "CABA"
    candidates = mutableSetOf(Candidate().apply {
        nombre = "Juan Carlos"
        partido = Peronista().apply {
            nombre = "FREJULI"
            afiliados = 12200
        }
    })
}