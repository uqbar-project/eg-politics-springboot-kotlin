package org.uqbar.politics.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Zona
import org.uqbar.politics.repository.ZonaRepository

@Service
class ZonaService {

    @Autowired
    lateinit var zonaRepository: ZonaRepository

    /*
 Alternativa con DTO

 fun getZonas(): List<ZonaPlanaDTO> = zonaRepository.findAll().map { zona -> ZonaPlanaDTO(zona.id!!, zona.descripcion) }
* */
    @Transactional(Transactional.TxType.NEVER)
    fun getZonas(): Iterable<Zona> = zonaRepository.findAll()

    @Transactional(Transactional.TxType.NEVER)
    fun getZona(id: Long): Zona = zonaRepository
        .findById(id)
        .orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "La zona con identificador $id no existe")
        }

}