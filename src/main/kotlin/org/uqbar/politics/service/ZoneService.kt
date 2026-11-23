package org.uqbar.politics.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Zone
import org.uqbar.politics.repository.ZoneRepository

@Service
class ZoneService {

    @Autowired
    lateinit var zoneRepository: ZoneRepository

    /*
 Alternativa con DTO

 fun getZones(): List<ZoneSummaryDTO> = zoneRepository.findAll().map { zone -> ZoneSummaryDTO(zone.id!!, zone.name) }
* */
    @Transactional(readOnly = true)
    fun getZones(): Iterable<Zone> = zoneRepository.findAll()

    @Transactional(readOnly = true)
    fun getZone(id: Long): Zone = zoneRepository
        .findById(id)
        .orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Zone with id $id not found")
        }

}