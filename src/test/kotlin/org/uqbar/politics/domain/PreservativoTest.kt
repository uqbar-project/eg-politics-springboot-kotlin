package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

class PreservativoTest {

    @Test
    fun `un partido preservativo sin nombre no pasa la validacion`() {
        assertThrows<UserException> { Preservativo().apply { nombre = "  " }.validar() }
    }

    @Test
    fun `un partido preservativo sin afiliados no pasa la validacion`() {
        assertThrows<UserException> { Preservativo().apply { nombre = "Elfo" }.validar() }
    }

    @Test
    fun `un partido preservativo correctamente definido pasa la validacion`() {
        partidoPreservativoOk().validar()
    }

    @Test
    fun `inicialmente un partido preservativo no tiene identificador`() {
        Assertions.assertNull(partidoPreservativoOk().id)
    }

    @Test
    fun `el toString de un partido preservativo esta basado en el nombre`() {
        val partido = partidoPreservativoOk()
        Assertions.assertEquals(partido.nombre, partido.toString())
    }
}

fun partidoPreservativoOk() = Preservativo().apply {
    nombre = "Elfo"
    afiliados = 10000
    fechaCreacion = LocalDate.now()
}