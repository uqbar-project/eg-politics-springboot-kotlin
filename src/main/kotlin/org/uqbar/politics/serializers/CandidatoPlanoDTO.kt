package org.uqbar.politics.serializers

import org.uqbar.politics.domain.Candidate

class CandidatoPlanoDTO(candidate: Candidate) {
    var partido = candidate.partido!!.nombre
    var nombre = candidate.nombre
    var votos = candidate.votos
}
