package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View

// Definir como default este serializador es una opción
// Otra es utilizar la variante del mapper por cada método del endpoint
// Y finalmente podemos construir nuestras sealed classes con DTOs
// @JsonSerialize(using=ZonaParaGrillaSerializer::class)
@Entity
class Zona {

    @Id
    @GeneratedValue
    @JsonView(View.Zona.Plana::class, View.Zona.Detalle::class)
    var id: Long? = null

    @Column(length=150)
    @JsonView(View.Zona.Plana::class, View.Zona.Detalle::class)
    lateinit var descripcion: String

    @OneToMany(fetch= FetchType.LAZY)
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