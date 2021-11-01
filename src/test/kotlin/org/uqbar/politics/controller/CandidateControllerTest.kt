package org.uqbar.politics.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
import org.uqbar.politics.repository.CandidateRepository

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de zonas")
class CandidateControllerTest {

    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repoCandidates: CandidateRepository

    private val CANDIDATE_NOMBRE = "Julio Sosa"

    @Test
    fun `podemos actualizar la información de una persona candidata`() {
        val candidate = repoCandidates.findByNombre(CANDIDATE_NOMBRE).get()
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

        // Pero ojo, como esto tiene efecto colateral, vamos a volver atrás el cambio
        candidate.votos = 0
        repoCandidates.save(candidate)
    }

}