package org.uqbar.politics.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.uqbar.politics.domain.Candidate
import java.util.*

interface CandidateRepository : CrudRepository<Candidate, Long> {

    fun findByNombre(nombre: String): Optional<Candidate>

    @EntityGraph(attributePaths = ["partido", "promesas", "opiniones"])
    override fun findById(id: Long): Optional<Candidate>
}