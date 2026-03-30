package org.uqbar.politics.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.uqbar.politics.domain.*
import org.uqbar.politics.repository.ZonaRepository
import org.uqbar.politics.repository.CandidateRepository
import org.uqbar.politics.repository.PartidoRepository

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de zonas")
class ZonaControllerTest {
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repoZonas: ZonaRepository

    @Autowired
    lateinit var repoCandidates: CandidateRepository

    @Autowired
    lateinit var repoPartidos: PartidoRepository

    @BeforeEach
    fun setup() {
        repoZonas.deleteAll()
        repoCandidates.deleteAll()
        repoPartidos.deleteAll()
        
        val partidoPeronista = crearPartidoPeronista("Partido Justicialista", 5000, true)
        val partidoPreservativo = crearPartidoPreservativo("Partido Conservador", 3000)
        
        val savedPartidoPeronista = repoPartidos.save(partidoPeronista)
        val savedPartidoPreservativo = repoPartidos.save(partidoPreservativo)
        
        val candidato1 = crearCandidato("Juan Pérez", savedPartidoPeronista, 100)
        val candidato2 = crearCandidato("María González", savedPartidoPreservativo, 80)
        
        val savedCandidato1 = repoCandidates.save(candidato1)
        val savedCandidato2 = repoCandidates.save(candidato2)
        
        val zonaConCandidatos = crearZona("Zona Norte", listOf(savedCandidato1, savedCandidato2))
        val zonaSinCandidatos = crearZona("Zona Sur", emptyList())
        
        repoZonas.saveAll(listOf(zonaConCandidatos, zonaSinCandidatos))
    }

    private fun crearPartidoPeronista(nombre: String, afiliados: Int, populista: Boolean): Peronista {
        return Peronista().apply {
            this.nombre = nombre
            this.afiliados = afiliados
            this.populista = populista
        }
    }

    private fun crearPartidoPreservativo(nombre: String, afiliados: Int): Preservativo {
        return Preservativo().apply {
            this.nombre = nombre
            this.afiliados = afiliados
        }
    }

    private fun crearCandidato(nombre: String, partido: Partido, votos: Int): Candidate {
        return Candidate().apply {
            this.nombre = nombre
            this.partido = partido
            this.votos = votos
        }
    }

    private fun crearZona(descripcion: String, candidates: List<Candidate>): Zona {
        return Zona().apply {
            this.descripcion = descripcion
            this.candidates = candidates.toMutableSet()
        }
    }

    @Test
    fun `las zonas solo traen los datos de primer nivel`() {
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zonas")).andReturn().response
        val zonas = mapper.readValue<List<Zona>>(responseEntity.contentAsString)
        assertEquals(200, responseEntity.status)
        assertEquals(2, zonas.size)
        
        val descripciones = zonas.map { it.descripcion }.sorted()
        assertEquals(listOf("Zona Norte", "Zona Sur"), descripciones)
        
        assertThrows<UninitializedPropertyAccessException> { zonas.first().candidates }
    }

    @Test
    fun `al traer el dato de una zona trae las personas candidatas tambien`() {
        val zonaConCandidatos = repoZonas.findAll().toList().find { it.descripcion == "Zona Norte" }
        assert(zonaConCandidatos != null) { "No se encontró la Zona Norte" }

        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zonas/${zonaConCandidatos!!.id}")).andReturn().response
        assertEquals(200, responseEntity.status)
        val zona = mapper.readValue<Zona>(responseEntity.contentAsString)

        assertEquals("Zona Norte", zona.descripcion)
        assertEquals(2, zona.candidates.size)

        val nombresCandidatos = zona.candidates.map { it.nombre }.sorted()
        assertEquals(listOf("Juan Pérez", "María González"), nombresCandidatos)

        assertTrue(zona.candidates.all { it.partido != null })
    }

    @Test
    @DisplayName("no podemos traer información de una zona inexistente")
    fun zonaInexistente() {
        val responseEntity = mockMvc.perform(MockMvcRequestBuilders.get("/zonas/999")).andReturn().response
        assertEquals(404, responseEntity.status)
    }

}