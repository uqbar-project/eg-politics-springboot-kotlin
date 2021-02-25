package org.uqbar.politics.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.module.SimpleModule
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Zona
import org.uqbar.politics.repository.ZonaRepository
import org.uqbar.politics.serializers.ZonaParaGrillaSerializer
import org.uqbar.politics.serializers.ZonaPlanaDTO

@RestController
@CrossOrigin(origins = ["*"], methods= [RequestMethod.GET])
class ZonaController {

    @Autowired
    private lateinit var zonaRepository: ZonaRepository

    @GetMapping("/zonas")
    @ApiOperation("Devuelve todas las zonas de votación")
    fun getZonas(): List<ZonaPlanaDTO> = zonaRepository.findAll().map { zona -> ZonaPlanaDTO(zona.id!!, zona.descripcion) }

    @GetMapping("/zonas/{id}")
    @ApiOperation("Muestra la información de una zona de votación con sus candidates")
    fun getZona(@PathVariable id: Long): Zona {
        mapper.registerModule(
            SimpleModule().addSerializer(ZonaParaGrillaSerializer())
        )

        return zonaRepository
            .findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "La zona con identificador $id no existe")
            }
    }

    companion object {
        val mapper: ObjectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
    }

}