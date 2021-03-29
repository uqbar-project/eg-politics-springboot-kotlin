package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View
import javax.persistence.*

@Entity
class Candidate {
    @Id @GeneratedValue
    @JsonView(View.Zona.Grilla::class)
    var id: Long? = null

    @Column(length=150)
    @JsonView(View.Zona.Grilla::class)
    var nombre = ""

    @ManyToOne
    @JsonView(View.Zona.Grilla::class)
    var partido: Partido? = null

    @JsonView(View.Zona.Grilla::class)
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