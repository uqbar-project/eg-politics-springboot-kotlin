package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class PeronistaTest {

    @Test
    fun `un partido peronista sin nombre no pasa la validacion`() {
        assertThrows<UserException> { Peronista().apply { nombre = "  " }.validar() }
    }

    @Test
    fun `un partido peronista sin afiliados no pasa la validacion`() {
        assertThrows<UserException> { Peronista().apply { nombre = "FREJUPO" }.validar() }
    }

    @Test
    fun `un partido peronista correctamente definido pasa la validacion`() {
        partidoPeronistaOk().validar()
    }

    @Test
    fun `inicialmente un partido peronista no tiene identificador`() {
        assertNull(partidoPeronistaOk().id)
    }

    @Test
    fun `el toString de un partido peronista esta basado en el nombre`() {
        val partido = partidoPeronistaOk()
        Assertions.assertEquals(partido.nombre, partido.toString())
    }
}

fun partidoPeronistaOk() = Peronista().apply {
    nombre = "FREJUPO"
    afiliados = 10000
    populista = true
}
