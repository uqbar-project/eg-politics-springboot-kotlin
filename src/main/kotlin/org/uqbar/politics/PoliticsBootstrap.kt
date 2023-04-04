package org.uqbar.politics

import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.uqbar.politics.domain.*
import org.uqbar.politics.repository.CandidateRepository
import org.uqbar.politics.repository.PartidoRepository
import org.uqbar.politics.repository.ZonaRepository

/**
 *
 * Para explorar otras opciones
 * https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
 */
@Service
class PoliticsBootstrap : InitializingBean {

    @Autowired
    private lateinit var repoZonas: ZonaRepository

    @Autowired
    private lateinit var repoPartidos: PartidoRepository

    @Autowired
    private lateinit var repoCandidates: CandidateRepository

    private lateinit var frejuli: Peronista
    private lateinit var perone: Peronista
    private lateinit var prime: Preservativo

    private lateinit var sosa: Candidate
    private lateinit var benitez: Candidate
    private lateinit var yapura: Candidate
    private lateinit var ramos: Candidate
    private lateinit var monti: Candidate
    private lateinit var rota: Candidate
    private lateinit var cafrune: Candidate

    private lateinit var nacional: Zona
    private lateinit var springfield: Zona

    fun initPartidos() {
        frejuli = Peronista().apply {
            afiliados = 60000
            nombre = "FREJULI"
            populista = true
        }

        perone = Peronista().apply {
            afiliados = 5000
            nombre = "Perone"
            populista = false
        }

        prime = Preservativo().apply {
            afiliados = 1200
            nombre = "PRIME"
            fechaCreacion = LocalDate.parse("2009-06-16")
        }

        this.crearPartidos(frejuli)
        this.crearPartidos(perone)
        this.crearPartidos(prime)
    }

    fun crearPartidos(partido: Partido) {
        val partidoEnRepo = repoPartidos.findByNombre(partido.nombre)
        if (partidoEnRepo.isPresent()) {
            partido.id = partidoEnRepo.get().id
        } else {
            repoPartidos.save(partido)
            println("Candidato ${partido.nombre} creado")
        }
    }

    fun initCandidatos() {
        sosa = Candidate().apply {
            nombre = "Julio Sosa"
            partido = frejuli
            agregarPromesa("Terminar con la inseguridad")
            agregarPromesa("Aborto para unos, banderitas para otros")
        }
        benitez = Candidate().apply {
            nombre = "Myriam Benitez"
            partido = frejuli
            agregarPromesa("Girar y girar hacia la libertad")
        }
        yapura = Candidate().apply {
            nombre = "Marcelo Yapura"
            partido = frejuli
            agregarPromesa("Terminar con la pobreza")
            agregarPromesa("Que todos los docentes de la UTN cobren en euros")
        }
        ramos = Candidate().apply {
            nombre = "Manuel Ramos"
            partido = perone
            agregarPromesa("Terminar con la inseguridad")
            agregarPromesa("Recuperar la confianza de los argentinos")
        }
        monti = Candidate().apply {
            nombre = "Yaco Monti"
            partido = perone
            agregarPromesa("Terminar con la inseguridad")
            agregarPromesa("Recuperar la dignidad")
        }
        rota = Candidate().apply {
            nombre = "Nino Rota"
            partido = prime
            agregarPromesa("Ganarle a la pobreza")
            agregarPromesa("Sacar el cepo a la moneda extranjera")
            agregarPromesa("Eliminar el impuesto a las ganancias")
        }
        cafrune = Candidate().apply {
            nombre = "Yamila Cafrune"
            partido = prime
            agregarPromesa("Que vuelva Futbol para Todos")
            agregarPromesa("Estatizar las empresas privadas")
            agregarPromesa("Pesificar la economia")
        }

        this.crearCandidate(sosa)
        this.crearCandidate(benitez)
        this.crearCandidate(yapura)
        this.crearCandidate(ramos)
        this.crearCandidate(monti)
        this.crearCandidate(rota)
        this.crearCandidate(cafrune)
    }

    fun crearCandidate(candidate: Candidate) {
        val candidateRepo = repoCandidates.findByNombre(candidate.nombre)
        if (candidateRepo.isPresent()) {
            candidate.id = candidateRepo.get().id
        } else {
            repoCandidates.save(candidate)
            println("Candidate ${candidate.nombre} creade")
        }
    }

    fun initZonas() {
        nacional = Zona().apply {
            descripcion = "Elecciones nacionales"
            candidates = hashSetOf(sosa, benitez, ramos, rota)
        }
        springfield = Zona().apply {
            descripcion = "Springfield"
            candidates = hashSetOf(yapura, monti, cafrune)
        }
        this.crearZona(nacional)
        this.crearZona(springfield)
    }

    fun crearZona(zona: Zona) {
        val listaZonas = repoZonas.findByDescripcion(zona.descripcion)
        if (listaZonas.isEmpty()) {
            repoZonas.save(zona)
            println("Zona ${zona.descripcion} creada")
        }
    }

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        this.initPartidos()
        this.initCandidatos()
        this.initZonas()
        println("------------------------------------------------------------------------")
    }

}