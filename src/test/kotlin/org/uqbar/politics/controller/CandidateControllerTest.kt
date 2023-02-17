package org.uqbar.politics.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.repository.CandidateRepository

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de zonas")
class CandidateControllerTest {

    private val mapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repoCandidates: CandidateRepository

    private val CANDIDATE_NOMBRE = "Julio Sosa"

    @Test
    @Transactional
    fun `podemos actualizar la informacion de una persona candidata`() {
        val candidate = getCandidateDePrueba()
        assertEquals(0, candidate.votos)
        candidate.reset()
        candidate.votar()
        val responseEntity = mockMvc.perform(
            MockMvcRequestBuilders.put("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(candidate))
        ).andReturn().response

        assertEquals(200, responseEntity.status)

        val candidateActualizado = repoCandidates.findByNombre(CANDIDATE_NOMBRE).get()
        assertEquals(1, candidateActualizado.votos)
    }

    @Test
    @Transactional
    fun `actualizar solo promesas para un candidato pisa las promesas anteriores y deja los votos como estaban`() {
        // ARRANGE
        // Creamos un candidato con 3 votos y 1 promesa
        val candidate = getCandidateDePrueba()
        repoCandidates.save(candidate.apply {
            votar()
            votar()
            votar()
            agregarPromesa("Terminar con la inseguridad")
        })

        // ACT
        // Actualizamos al candidato
        // - no pasamos votos
        // - pasamos una nueva lista de promesas
        mockMvc.perform(
            MockMvcRequestBuilders.put("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Candidate().apply {
                    agregarPromesa("Promocionar a todes les alumnes")
                }))
        )
        .andExpect(status().isOk)

        // ASSERT
        // - los votos siguen igual
        // - la lista de promesas se sobreescribió
        val candidateModificado = repoCandidates.findById(candidate.id!!).orElseThrow { UserException("Candidate " + candidate.id + " no encontrado")}
        assertEquals("Promocionar a todes les alumnes", candidateModificado.promesas.first().accionPrometida)
        assertEquals(3, candidateModificado.votos)
    }

    @Test
    @Transactional
    fun `actualizar un candidate inexistente devuelve un codigo de http 404`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/candidates/3253532")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Candidate().apply {
                    agregarPromesa("Promocionar a todes les alumnes")
                }))
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `si buscamos un candidate inexistente, recibimos un error 404 de http`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/candidates/22141")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `si buscamos un candidate existente, recibimos el candidate`() {
        val candidate = getCandidateDePrueba()
        mockMvc.perform(
            MockMvcRequestBuilders.get("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk)
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.nombre").value(CANDIDATE_NOMBRE))
    }

    fun getCandidateDePrueba() = repoCandidates.findByNombre(CANDIDATE_NOMBRE).get()
}
