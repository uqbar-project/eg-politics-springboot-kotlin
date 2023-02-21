package org.uqbar.politics.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.repository.CandidateRepository

@Service
class CandidateService {

    @Autowired
    lateinit var candidateRepository: CandidateRepository

    @Transactional(Transactional.TxType.NEVER)
    fun getCandidate(id: Long): Candidate =
        candidateRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
        }

    @Transactional(Transactional.TxType.REQUIRED)
    fun actualizarCandidate(candidateNuevo: Candidate, id: Long): Candidate {
        return candidateRepository
            .findById(id)
            .map { candidate ->
                // solo modificamos lo que está disponible para cambiar en la aplicación
                candidate.actualizarPromesas(candidateNuevo.promesas)
                if (candidateNuevo.votos > 0) {
                    candidate.votos = candidateNuevo.votos
                }
                // el save no es obligatorio si utilizamos las anotaciones transactional
                candidateRepository.save(candidate)
                //
                candidate
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
            }

    }
}