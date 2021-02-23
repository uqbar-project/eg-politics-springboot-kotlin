package org.uqbar.politics.domain

import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Promesa {

    @Id @GeneratedValue
    var id: Long? = null

    @Column
    var fecha: LocalDate = LocalDate.now()

    @Column(length=255)
    lateinit var accionPrometida: String

    // constructor necesario para JPA / Hibernate
    constructor() {
    }

    constructor(accionPrometida: String) {
        this.accionPrometida = accionPrometida
    }

    fun validar() {
        if (accionPrometida.trim().equals("")) {
            throw UserException("Debe ingresar una acción en la promesa")
        }
    }

}
