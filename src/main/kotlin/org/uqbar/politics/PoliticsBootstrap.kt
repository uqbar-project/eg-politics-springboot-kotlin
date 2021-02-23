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
        frejuli = Peronista()
        frejuli.afiliados = 60000
        frejuli.nombre = "FREJULI"
        frejuli.populista = true

        perone = Peronista()
        perone.afiliados = 5000
        perone.nombre = "Perone"
        perone.populista = false

        prime = Preservativo()
        prime.afiliados = 1200
        prime.nombre = "PRIME"
        prime.fechaCreacion = LocalDate.parse("2009-06-16")

        this.crearPartidos(frejuli)
        this.crearPartidos(perone)
        this.crearPartidos(prime)
    }

    fun crearPartidos(partido: Partido) {
        val partidoEnRepo = repoPartidos.findByNombre(partido.nombre)
        if (partidoEnRepo.isPresent()) {
            partido.id = partidoEnRepo.get().id
        }
        repoPartidos.save(partido)
        println("Candidato " + partido.nombre + " creado")
    }

    fun initCandidatos() {
        sosa = Candidate()
        sosa.nombre = "Julio Sosa"
        sosa.partido = frejuli
        sosa.agregarPromesa("Terminar con la inseguridad")
        sosa.agregarPromesa("Aborto para unos, banderitas para otros")
        benitez = Candidate()
        benitez.nombre = "Myriam Benitez"
        benitez.partido = frejuli
        benitez.agregarPromesa("Girar y girar hacia la libertad")
        yapura = Candidate()
        yapura.nombre = "Marcelo Yapura"
        yapura.partido = frejuli
        yapura.agregarPromesa("Terminar con la pobreza")
        yapura.agregarPromesa("Que todos los docentes de la UTN cobren en euros")
        ramos = Candidate()
        ramos.nombre = "Manuel Ramos"
        ramos.partido = perone
        ramos.agregarPromesa("Terminar con la inseguridad")
        ramos.agregarPromesa("Recuperar la confianza de los argentinos")
        monti = Candidate()
        monti.nombre = "Yaco Monti"
        monti.partido = perone
        monti.agregarPromesa("Terminar con la inseguridad")
        monti.agregarPromesa("Recuperar la dignidad")
        rota = Candidate()
        rota.nombre = "Nino Rota"
        rota.partido = prime
        rota.agregarPromesa("Ganarle a la pobreza")
        rota.agregarPromesa("Sacar el cepo a la moneda extranjera")
        rota.agregarPromesa("Eliminar el impuesto a las ganancias")
        cafrune = Candidate()
        cafrune.nombre = "Yamila Cafrune"
        cafrune.partido = prime
        cafrune.agregarPromesa("Que vuelva Futbol para Todos")
        cafrune.agregarPromesa("Estatizar las empresas privadas")
        cafrune.agregarPromesa("Pesificar la economia")

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
        }
        repoCandidates.save(candidate)
        println("Candidate " + candidate.nombre + if (candidateRepo.isPresent()) " creade" else " actualizade")
    }

    fun initZonas() {
        nacional = Zona()
        nacional.descripcion = "Elecciones nacionales"
        nacional.candidates = hashSetOf(sosa, benitez, ramos, rota)
        springfield = Zona()
        springfield.descripcion = "Springfield"
        springfield.candidates = hashSetOf(yapura, monti, cafrune)
        this.crearZona(nacional)
        this.crearZona(springfield)
    }

    fun crearZona(zona: Zona) {
        val listaZonas = repoZonas.findByDescripcion(zona.descripcion)
        if (listaZonas.isEmpty()) {
            repoZonas.save(zona)
            println("Zona " + zona.descripcion + " creada")
        } else {
            val zonaBD = listaZonas.first()
            zona.id = zonaBD.id
            repoZonas.save(zona)
        }
    }

    override fun afterPropertiesSet() {
        println("************************************************************************")
        println("Running initialization")
        println("************************************************************************")
        this.initPartidos()
        this.initCandidatos()
        this.initZonas()
    }

}