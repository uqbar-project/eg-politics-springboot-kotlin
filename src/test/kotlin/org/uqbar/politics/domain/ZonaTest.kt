package org.uqbar.politics.domain

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
        assertThrows<UserException> { Zona().apply { descripcion = "Zona" }.validar() }
    }

    @Test
    fun `una zona inicialmente no tiene identificador`() {
        assertThrows<UserException> { zonaOk().validar() }
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