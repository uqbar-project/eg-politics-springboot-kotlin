package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate
import javax.persistence.*

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Peronista::class, name = "PJ"),
    JsonSubTypes.Type(value = Preservativo::class, name = "PRE")
)
@Inheritance(strategy= InheritanceType.JOINED)
abstract class Partido {

    @Id @GeneratedValue
    var id: Long? = null

    @Column(length=150)
    lateinit var nombre: String

    @Column
    var afiliados: Int = 0

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
