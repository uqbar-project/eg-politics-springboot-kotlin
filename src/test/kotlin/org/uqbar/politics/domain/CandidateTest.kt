package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class CandidateTest {

    @Test
    fun `un candidate sin nombre no pasa la validacion`() {
        assertThrows<UserException> { Candidate().validar() }
    }

    @Test
    fun `un candidate sin partido no pasa la validacion`() {
        assertThrows<UserException> { Candidate().apply { nombre = "Pierino Fantaguzzi" }.validar() }
    }

    @Test
    fun `un candidate con datos correctos pasa la validacion`() {
        candidateOk().validar()
    }

    @Test
    fun `un candidate cuando es votado incrementa sus votos`() {
        val candidate = candidateOk()
        val votosAnteriores = candidate.votos
        candidate.votar()
        Assertions.assertEquals(votosAnteriores + 1, candidate.votos)
    }

    @Test
    fun `un candidate puede agregar promesas`() {
        val candidate = candidateOk()
        val promesasAnteriores = candidate.promesas.size
        candidate.agregarPromesa("Promocionar a todes les alumnes")
        Assertions.assertEquals(promesasAnteriores + 1, candidate.promesas.size)
    }

    @Test
    fun `al actualizar promesas de une candidate se blanquean las anteriores`() {
        val candidate = candidateOk()
        candidate.agregarPromesa("Promocionar a todes les alumnes")
        candidate.actualizarPromesas(listOf(Promesa("Terminar con las clases presenciales"), Promesa("Evitar exámenes capciosos")))
        Assertions.assertEquals(2, candidate.promesas.size)
    }

    @Test
    fun `al resetear se blanquean promesas de une candidate`() {
        val candidate = candidateOk()
        candidate.agregarPromesa("Promocionar a todes les alumnes")
        candidate.reset()
        Assertions.assertEquals(0, candidate.promesas.size)
    }

    @Test
    fun `al resetear se blanquean opiniones de une candidate`() {
        val candidate = candidateOk()
        candidate.agregarOpinion("Parece gracioso")
        candidate.reset()
        Assertions.assertEquals(0, candidate.opiniones.size)
    }

    @Test
    fun `el toString de candidate es su nombre`() {
        val candidate = candidateOk()
        Assertions.assertEquals(candidate.nombre, candidate.toString())
    }
}

fun candidateOk() = Candidate().apply {
    nombre = "Pierino Fantaguzzi"
    partido = Peronista()
}