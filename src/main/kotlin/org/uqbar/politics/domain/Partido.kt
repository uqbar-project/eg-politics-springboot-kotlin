package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View
import java.time.LocalDate

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Peronista::class, name = "PJ"),
    JsonSubTypes.Type(value = Preservativo::class, name = "PRE")
)
@Inheritance(strategy= InheritanceType.JOINED)
abstract class Partido {

    @Id @GeneratedValue
    @JsonView(View.Zona.Detalle::class)
    open var id: Long? = null

    @Column(length=150)
    @JsonView(View.Zona.Detalle::class)
    open lateinit var nombre: String

    @Column
    open var afiliados: Int = 0

    open fun validar() {
        if (nombre.trim() == "") {
            throw UserException("Debe ingresar nombre")
        }
        if (afiliados < 1000) {
            throw UserException("El partido no tiene suficientes afiliados")
        }
    }

    override fun toString(): String = nombre

}

@Entity
class Peronista : Partido() {
    @Column
    var populista = false
}

@Entity
class Preservativo : Partido() {
    @Column
    var fechaCreacion: LocalDate = LocalDate.now()

    override fun validar() {
        super.validar()
        if (fechaCreacion.isAfter(LocalDate.now())) {
            throw UserException("La fecha de creación debe ser anterior a la de hoy")
        }
    }

}
