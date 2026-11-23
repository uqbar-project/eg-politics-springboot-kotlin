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
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.repository.CandidateRepository

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Zone controller")
class CandidateControllerTest {

    private val mapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var candidateRepository: CandidateRepository

    private val CANDIDATE_NAME = "Julio Sosa"

    @Test
    @Transactional
    fun `can update candidate information`() {
        val candidate = getTestCandidate()
        assertEquals(0, candidate.votes)
        candidate.reset()
        candidate.vote()
        val responseEntity = mockMvc.perform(
            MockMvcRequestBuilders.patch("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(candidate))
        ).andReturn().response

        assertEquals(200, responseEntity.status)

        val updatedCandidateResult = candidateRepository.findByName(CANDIDATE_NAME).get()
        assertEquals(1, updatedCandidateResult.votes)
    }

    @Test
    @Transactional
    fun `updating only promises replaces promises and keeps votes`() {
        // ARRANGE
        // Creamos un candidate con 3 votes y 1 promise
        val candidate = getTestCandidate()
        candidateRepository.save(candidate.apply {
            vote()
            vote()
            vote()
            addPromise("Terminar con la inseguridad")
        })

        // ACT
        // Actualizamos al candidate
        // - no pasamos votes
        // - pasamos una nueva lista de promises
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Candidate().apply {
                    addPromise("Promocionar a todes les alumnes")
                }))
        )
        .andExpect(status().isOk)

        // ASSERT
        // - los votes siguen igual
        // - la lista de promises se sobreescribió
        val modifiedCandidate = candidateRepository.findById(candidate.id!!).orElseThrow { UserException("Candidate " + candidate.id + " no encontrado")}
        assertEquals("Promocionar a todes les alumnes", modifiedCandidate.promises.first().action)
        assertEquals(3, modifiedCandidate.votes)
    }

    @Test
    @Transactional
    fun `updating a nonexistent candidate returns 404`() {
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/candidates/3253532")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Candidate().apply {
                    addPromise("Promocionar a todes les alumnes")
                }))
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `getting a nonexistent candidate returns 404`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/candidates/22141")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `getting an existing candidate returns the candidate`() {
        val candidate = getTestCandidate()
        mockMvc.perform(
            MockMvcRequestBuilders.get("/candidates/" + candidate.id)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk)
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.name").value(CANDIDATE_NAME))
    }

    fun getTestCandidate() = candidateRepository.findByName(CANDIDATE_NAME).get()
}
