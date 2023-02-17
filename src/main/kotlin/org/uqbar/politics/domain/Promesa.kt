package org.uqbar.politics.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

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
