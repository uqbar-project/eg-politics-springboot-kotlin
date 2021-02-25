package org.uqbar.politics.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.uqbar.politics.domain.Zona
import java.util.*

interface ZonaRepository : CrudRepository<Zona, Long> {
    fun findByDescripcion(descripcion: String): List<Zona>

    @EntityGraph(attributePaths=["candidates.promesas", "candidates.partido", "candidates.opiniones"])
    override fun findById(id: Long): Optional<Zona>

}