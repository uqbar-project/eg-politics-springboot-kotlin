package org.uqbar.politics.repository

import org.springframework.data.repository.CrudRepository
import org.uqbar.politics.domain.Partido
import java.util.*

interface PartidoRepository : CrudRepository<Partido, Long> {
    fun findByNombre(nombre: String): Optional<Partido>
}