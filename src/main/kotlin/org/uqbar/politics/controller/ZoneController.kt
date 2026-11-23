package org.uqbar.politics.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.uqbar.politics.domain.Zone
import org.uqbar.politics.serializers.View
import org.uqbar.politics.service.ZoneService

@RestController
@RequestMapping("/zones")
class ZoneController {

    @Autowired
    private lateinit var zoneService: ZoneService

    @GetMapping("")
    @JsonView(View.Zone.Plain::class)
    fun getZones(): Iterable<Zone> = zoneService.getZones()

    @GetMapping("/{id}")
    @JsonView(View.Zone.Detail::class)
    fun getZone(@PathVariable id: Long): Zone = zoneService.getZone(id)

    /*
    Alternativa con serializadores

    fun getZone(@PathVariable id: Long): Zone {
       mapper.registerModule(
           SimpleModule().addSerializer(ZonaParaGrillaSerializer())
       )
       return zoneService.getZone(id)
    */
}