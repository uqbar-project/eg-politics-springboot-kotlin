package org.uqbar.politics.controller

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.repository.CandidateRepository

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class CandidateController {

    @Autowired
    private lateinit var candidateRepository: CandidateRepository

    @GetMapping("/candidates/{id}")
    @ApiOperation("Permite conocer los datos de una persona candidata en base al identificador.")
    fun getCandidate(@PathVariable id: Long): Candidate =
        candidateRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
        }

    @PutMapping("/candidates/{id}")
    @ApiOperation("Permite actualizar las promesas o los votos de una persona candidata.")
    fun actualizarCandidato(@RequestBody candidateNuevo: Candidate, @PathVariable id: Long): ResponseEntity<String> {
        candidateRepository
            .findById(id)
            .map { candidate ->
                // solo modificamos lo que está disponible para cambiar en la aplicación
                candidate.actualizarPromesas(candidateNuevo.promesas)
                if (candidateNuevo.votos > 0) {
                    candidate.votos = candidateNuevo.votos
                }
                candidateRepository.save(candidate)
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "El candidato con identificador $id no existe")
            }

        return ResponseEntity.ok().body("El candidato fue actualizado correctamente")
    }

}