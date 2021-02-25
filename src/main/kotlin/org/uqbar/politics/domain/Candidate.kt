package org.uqbar.politics.domain

import org.uqbar.politics.domain.exceptions.UserException
import javax.persistence.*

@Entity
class Candidate {
    @Id @GeneratedValue
    var id: Long? = null

    @Column(length=150)
    var nombre = ""

    @ManyToOne
    var partido: Partido? = null

    var votos = 0

    @OneToMany(fetch=FetchType.LAZY, cascade= [CascadeType.ALL])
    @OrderColumn
    var promesas = mutableListOf<Promesa>()

    @ElementCollection
    @OrderColumn
    var opiniones = mutableListOf<String>()

    fun validar() {
        if (nombre.trim() == "") {
            throw UserException("Debe ingresar descripcion")
        }
        if (partido === null) {
            throw UserException("El candidato debe estar participando en un partido político")
        }
    }

    override fun toString(): String = nombre

    fun agregarPromesa(nuevaPromesa: String) {
        promesas.add(Promesa(nuevaPromesa))
    }

    fun actualizarPromesas(nuevasPromesas: List<Promesa>) {
        if (nuevasPromesas.isNotEmpty()) {
            promesas.clear()
            promesas.addAll(nuevasPromesas)
        }
    }

    fun agregarOpinion(opinion: String) {
        opiniones.add(opinion)
    }

    fun votar() {
        votos++
    }

    fun reset() {
        promesas = mutableListOf()
        opiniones = mutableListOf()
    }
}