package org.uqbar.politics.domain

import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Promesa(@Column(length = 255) var accionPrometida: String = "") {

    @Id @GeneratedValue
    var id: Long? = null

    @Column
    var fecha: LocalDate = LocalDate.now()

    fun validar() {
        if (accionPrometida.trim() == "") {
            throw UserException("Debe ingresar una acción en la promesa")
        }
    }

}
