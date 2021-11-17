package org.uqbar.politics.controller

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.service.CandidateService

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class CandidateController {

    @Autowired
    private lateinit var candidateService: CandidateService

    @GetMapping("/candidates/{id}")
    @ApiOperation("Permite conocer los datos de una persona candidata en base al identificador.")
    fun getCandidate(@PathVariable id: Long) =
        candidateService.getCandidate(id)

    @PutMapping("/candidates/{id}")
    @ApiOperation("Permite actualizar las promesas o los votos de una persona candidata.")
    fun actualizarCandidato(@RequestBody candidateNuevo: Candidate, @PathVariable id: Long): Candidate =
        candidateService.actualizarCandidato(candidateNuevo, id)

}