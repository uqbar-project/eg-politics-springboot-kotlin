package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View

@Entity
class Candidate {
    @Id
    @GeneratedValue
    @JsonView(View.Zona.Detalle::class)
    var id: Long? = null

    @JsonView(View.Zona.Detalle::class)
    var nombre = ""

    @ManyToOne
    @JsonView(View.Zona.Detalle::class)
    var partido: Partido? = null

    @JsonView(View.Zona.Detalle::class)
    var votos = 0

    @OneToMany(cascade = [CascadeType.ALL])
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