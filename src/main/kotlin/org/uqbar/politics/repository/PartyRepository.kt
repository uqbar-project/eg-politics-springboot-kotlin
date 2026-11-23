package org.uqbar.politics.repository

import org.springframework.data.repository.CrudRepository
import org.uqbar.politics.domain.Party
import java.util.*

interface PartyRepository : CrudRepository<Party, Long> {
    fun findByName(name: String): Optional<Party>
}