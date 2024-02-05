package org.uqbar.politics.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.uqbar.politics.domain.Candidate
import org.uqbar.politics.service.CandidateService

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class CandidateController {

    @Autowired
    private lateinit var candidateService: CandidateService

    @GetMapping("/candidates/{id}")
    fun getCandidate(@PathVariable id: Long) =
        candidateService.getCandidate(id)

    @PutMapping("/candidates/{id}")
    fun actualizarCandidate(@RequestBody candidateNuevo: Candidate, @PathVariable id: Long): Candidate =
        candidateService.actualizarCandidate(candidateNuevo, id)

}