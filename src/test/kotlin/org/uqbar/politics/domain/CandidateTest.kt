package org.uqbar.politics.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.uqbar.politics.domain.exceptions.UserException

class CandidateTest {

    @Test
    fun `un candidate sin name no pasa la validacion`() {
        assertThrows<UserException> { Candidate().validate() }
    }

    @Test
    fun `candidate without party fails validation`() {
        assertThrows<UserException> { Candidate().apply { name = "Pierino Fantaguzzi" }.validate() }
    }

    @Test
    fun `candidate with valid data passes validation`() {
        validCandidate().validate()
    }

    @Test
    fun `candidate increments votes when voted`() {
        val candidate = validCandidate()
        val previousVotes = candidate.votes
        candidate.vote()
        Assertions.assertEquals(previousVotes + 1, candidate.votes)
    }

    @Test
    fun `candidate can add promises`() {
        val candidate = validCandidate()
        val previousPromisesCount = candidate.promises.size
        candidate.addPromise("Promocionar a todes les alumnes")
        Assertions.assertEquals(previousPromisesCount + 1, candidate.promises.size)
    }

    @Test
    fun `updating candidate promises replaces previous promises`() {
        val candidate = validCandidate()
        candidate.addPromise("Promocionar a todes les alumnes")
        candidate.updatePromises(listOf(Promise("Terminar con las clases presenciales"), Promise("Evitar exámenes capciosos")))
        Assertions.assertEquals(2, candidate.promises.size)
    }

    @Test
    fun `reset clears candidate promises`() {
        val candidate = validCandidate()
        candidate.addPromise("Promocionar a todes les alumnes")
        candidate.reset()
        Assertions.assertEquals(0, candidate.promises.size)
    }

    @Test
    fun `reset clears candidate opinions`() {
        val candidate = validCandidate()
        candidate.addOpinion("Parece gracioso")
        candidate.reset()
        Assertions.assertEquals(0, candidate.opinions.size)
    }

    @Test
    fun `el toString de candidate es su name`() {
        val candidate = validCandidate()
        Assertions.assertEquals(candidate.name, candidate.toString())
    }
}

fun validCandidate() = Candidate().apply {
    name = "Pierino Fantaguzzi"
    party = PeronistParty()
}