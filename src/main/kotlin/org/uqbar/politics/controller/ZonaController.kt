package org.uqbar.politics.controller

import com.fasterxml.jackson.annotation.JsonView
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.uqbar.politics.domain.Zona
import org.uqbar.politics.serializers.View
import org.uqbar.politics.service.ZonaService

@RestController
@CrossOrigin(origins = ["*"], methods= [RequestMethod.GET])
class ZonaController {

    @Autowired
    private lateinit var zonaService: ZonaService

    @GetMapping("/zonas")
    @ApiOperation("Devuelve todas las zonas de votación")
    @JsonView(View.Zona.Plana::class)
    fun getZonas(): Iterable<Zona> = zonaService.getZonas()

    @GetMapping("/zonas/{id}")
    @ApiOperation("Muestra la información de una zona de votación con sus candidates")
    @JsonView(View.Zona.Grilla::class)
    fun getZona(@PathVariable id: Long): Zona = zonaService.getZona(id)

}