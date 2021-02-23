package org.uqbar.politics.serializers

import org.uqbar.politics.domain.Candidate

class CandidatoPlanoDTO(candidate: Candidate) {
    var id = candidate.id
    var partido = candidate.partido!!.nombre
    var nombre = candidate.nombre
    var votos = candidate.votos
}
