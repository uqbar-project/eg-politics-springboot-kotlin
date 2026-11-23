package org.uqbar.politics

import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.uqbar.politics.domain.*
import org.uqbar.politics.repository.CandidateRepository
import org.uqbar.politics.repository.PartyRepository
import org.uqbar.politics.repository.ZoneRepository

/**
 *
 * Para explorar otras opciones
 * https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
 */
@Service
class PoliticsBootstrap : InitializingBean {

    @Autowired
    private lateinit var zoneRepository: ZoneRepository

    @Autowired
    private lateinit var partyRepository: PartyRepository

    @Autowired
    private lateinit var candidateRepository: CandidateRepository

    private lateinit var frejuli: PeronistParty
    private lateinit var perone: PeronistParty
    private lateinit var prime: PreservativeParty

    private lateinit var sosa: Candidate
    private lateinit var benitez: Candidate
    private lateinit var yapura: Candidate
    private lateinit var ramos: Candidate
    private lateinit var monti: Candidate
    private lateinit var rota: Candidate
    private lateinit var cafrune: Candidate

    private lateinit var nacional: Zone
    private lateinit var springfield: Zone

    fun initParties() {
        frejuli = PeronistParty().apply {
            members = 60000
            name = "FREJULI"
            populist = true
        }

        perone = PeronistParty().apply {
            members = 5000
            name = "Perone"
            populist = false
        }

        prime = PreservativeParty().apply {
            members = 1200
            name = "PRIME"
            foundationDate = LocalDate.parse("2009-06-16")
        }

        this.createParty(frejuli)
        this.createParty(perone)
        this.createParty(prime)
    }

    fun createParty(party: Party) {
        val existingParty = partyRepository.findByName(party.name)
        if (existingParty.isPresent()) {
            party.id = existingParty.get().id
        } else {
            partyRepository.save(party)
            println("Party ${party.name} created")
        }
    }

    fun initCandidates() {
        sosa = Candidate().apply {
            name = "Julio Sosa"
            party = frejuli
            addPromise("Terminar con la inseguridad")
            addPromise("Aborto para unos, banderitas para otros")
        }
        benitez = Candidate().apply {
            name = "Myriam Benitez"
            party = frejuli
            addPromise("Girar y girar hacia la libertad")
        }
        yapura = Candidate().apply {
            name = "Marcelo Yapura"
            party = frejuli
            addPromise("Terminar con la pobreza")
            addPromise("Que todos los docentes de la UTN cobren en euros")
        }
        ramos = Candidate().apply {
            name = "Manuel Ramos"
            party = perone
            addPromise("Terminar con la inseguridad")
            addPromise("Recuperar la confianza de los argentinos")
        }
        monti = Candidate().apply {
            name = "Yaco Monti"
            party = perone
            addPromise("Terminar con la inseguridad")
            addPromise("Recuperar la dignidad")
        }
        rota = Candidate().apply {
            name = "Nino Rota"
            party = prime
            addPromise("Ganarle a la pobreza")
            addPromise("Sacar el cepo a la moneda extranjera")
            addPromise("Eliminar el impuesto a las ganancias")
        }
        cafrune = Candidate().apply {
            name = "Yamila Cafrune"
            party = prime
            addPromise("Que vuelva Futbol para Todos")
            addPromise("Estatizar las empresas privadas")
            addPromise("Pesificar la economia")
        }

        this.createCandidate(sosa)
        this.createCandidate(benitez)
        this.createCandidate(yapura)
        this.createCandidate(ramos)
        this.createCandidate(monti)
        this.createCandidate(rota)
        this.createCandidate(cafrune)
    }

    fun createCandidate(candidate: Candidate) {
        val existingCandidate = candidateRepository.findByName(candidate.name)
        if (existingCandidate.isPresent()) {
            candidate.id = existingCandidate.get().id
        } else {
            candidateRepository.save(candidate)
            println("Candidate ${candidate.name} created")
        }
    }

    fun initZones() {
        nacional = Zone().apply {
            name = "Elecciones nacionales"
            candidates = hashSetOf(sosa, benitez, ramos, rota)
        }
        springfield = Zone().apply {
            name = "Springfield"
            candidates = hashSetOf(yapura, monti, cafrune)
        }
        this.createZone(nacional)
        this.createZone(springfield)
    }

    fun createZone(zone: Zone) {
        val existingZones = zoneRepository.findByName(zone.name)
        if (existingZones.isEmpty()) {
            zoneRepository.save(zone)
            println("Zone ${zone.name} created")
        }
    }

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        this.initParties()
        this.initCandidates()
        this.initZones()
        println("------------------------------------------------------------------------")
    }

}