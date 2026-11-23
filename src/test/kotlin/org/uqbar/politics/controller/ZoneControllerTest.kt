package org.uqbar.politics.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.uqbar.politics.domain.Zone
import org.uqbar.politics.repository.ZoneRepository

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Zone controller")
class ZoneControllerTest {
    private val mapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var zoneRepository: ZoneRepository

    @Test
    fun `zones list returns plain data only`() {
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zones")).andReturn().response
        val zones = mapper.readValue<List<Zone>>(responseEntity.contentAsString)
        assertEquals(200, responseEntity.status)
        assertEquals(2, zones.size)
        // zones should not include candidates
        assertThrows<UninitializedPropertyAccessException> { zones.first().candidates }
    }

    @Test
    fun `zone detail includes candidates`() {
        val zones = zoneRepository.findAll().toList()
        assert(zones.isNotEmpty(), { "No hay zones cargadas en el sistema" })
        val zoneId = zones.first().id
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zones/$zoneId")).andReturn().response
        assertEquals(200, responseEntity.status)
        val zone = mapper.readValue<Zone>(responseEntity.contentAsString)
        assert(zone.candidates.isNotEmpty()) { "Zone should have candidates" }
    }

    @Test
    @DisplayName("getting a nonexistent zone returns 404")
    fun `getting a nonexistent zone returns 404`() {
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zones/100")).andReturn().response
        assertEquals(404, responseEntity.status)
    }

}