package org.uqbar.politics.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.repository.CandidateRepository

@RestController
@CrossOrigin("*")
class CandidateController {

    @Autowired
    private lateinit var candidateRepository: CandidateRepository

    @GetMapping("/candidatos/{id}")
    fun getCandidato(@PathVariable id: Long) {
        this.candidateRepository.findById(id)
    }

    @PutMapping("/candidatos/{id}")
    fun actualizarCandidato(@RequestBody candidatoNuevo: Candidate, @PathVariable id: Long): ResponseEntity<String> {

        candidateRepository
            .findById(id)
            .map { candidate ->
                // solo modificamos lo que está disponible para cambiar en la aplicación
                candidate.actualizarPromesas(candidatoNuevo.promesas ?: emptyList())
                candidate.votos = candidatoNuevo.votos
                candidateRepository.save(candidate)
            }
            .orElseThrow{
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador " + id + " no existe")
            }

        return ResponseEntity.ok().body("El candidato fue actualizado correctamente")
    }

}