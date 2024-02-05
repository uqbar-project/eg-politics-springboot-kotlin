package org.uqbar.politics.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.uqbar.politics.domain.Zona
import org.uqbar.politics.serializers.View
import org.uqbar.politics.service.ZonaService

@RestController
@CrossOrigin(origins = ["*"], methods= [RequestMethod.GET, RequestMethod.OPTIONS])
@RequestMapping("/zonas")
class ZonaController {

    @Autowired
    private lateinit var zonaService: ZonaService

    @GetMapping("")
    @JsonView(View.Zona.Plana::class)
    fun getZonas(): Iterable<Zona> = zonaService.getZonas()

    @GetMapping("/{id}")
    @JsonView(View.Zona.Detalle::class)
    fun getZona(@PathVariable id: Long): Zona = zonaService.getZona(id)

    /*
    Alternativa con serializadores

    fun getZona(@PathVariable id: Long): Zona {
       mapper.registerModule(
           SimpleModule().addSerializer(ZonaParaGrillaSerializer())
       )
       return zonaService.getZona(id)
    */
}