package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View

@Entity
class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Zona.Plana::class, View.Zona.Detalle::class)
    var id: Long? = null

    @Column(length = 50)
    @JsonView(View.Zona.Plana::class, View.Zona.Detalle::class)
    lateinit var descripcion: String

    @OneToMany
    @JsonView(View.Zona.Detalle::class)
    lateinit var candidates: MutableSet<Candidate>

    fun validar() {
        if (descripcion.trim() == "") {
            throw UserException("Debe ingresar descripcion")
        }
        if (candidates.isEmpty()) {
            throw UserException("Debe haber al menos un candidato en la zona")
        }
    }

    override fun toString(): String = descripcion
}