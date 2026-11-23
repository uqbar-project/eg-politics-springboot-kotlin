package org.uqbar.politics.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.repository.CandidateRepository

@Service
class CandidateService {

    @Autowired
    lateinit var candidateRepository: CandidateRepository

    fun getCandidate(id: Long): Candidate =
        candidateRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
        }

    // https://stackoverflow.com/questions/7125837/why-does-transaction-roll-back-on-runtimeexception-but-not-sqlexception
    @Transactional(rollbackFor = [Exception::class])
    fun actualizarCandidate(candidateNuevo: Candidate, id: Long): Candidate {
        return candidateRepository
            .findById(id)
            .map { candidate ->
                // solo modificamos lo que está disponible para cambiar en la aplicación
                candidate.actualizarPromesas(candidateNuevo.promesas)
                if (candidateNuevo.votos > 0) {
                    candidate.votos = candidateNuevo.votos
                }
                // el save no es obligatorio si utilizamos la anotación
                // candidateRepository.save(candidate)
                // rollbackea por una runtime exception
                // throw RuntimeException("Che, se rompió todo, se rompió")
                // no rollbackea por una Exception
                // y sí por una SQL Exception
                //
                candidate
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
            }

    }
}