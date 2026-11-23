package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

class PromesaTest {

    @Test
    fun `una promesa sin accion prometida o en blanco no pasa la validacion`() {
        assertThrows<UserException> { Promesa().validar() }
        assertThrows<UserException> { Promesa(accionPrometida = "").validar() }
        assertThrows<UserException> { Promesa(accionPrometida = "   ").validar() }
    }

    @Test
    fun `una promesa correctamente configurada pasa la validacion`() {
        Promesa(accionPrometida = "Terminar con xxx").validar()
    }

    @Test
    fun `una promesa no tiene inicialmente identificador y toma la fecha del dia`() {
        val promesa = Promesa(accionPrometida = "Terminar con xxx")
        assertNull(promesa.id)
        Assertions.assertEquals(LocalDate.now(), promesa.fecha)
    }
}