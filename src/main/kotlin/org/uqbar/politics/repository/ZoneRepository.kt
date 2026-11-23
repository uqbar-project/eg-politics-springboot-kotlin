package org.uqbar.politics.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.uqbar.politics.domain.Zone
import java.util.*

interface ZoneRepository : CrudRepository<Zone, Long> {
    fun findByName(name: String): List<Zone>

    @EntityGraph(attributePaths = ["candidates.party"])
    override fun findById(id: Long): Optional<Zone>

}