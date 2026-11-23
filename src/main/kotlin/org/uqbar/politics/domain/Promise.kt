package org.uqbar.politics.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.uqbar.politics.domain.exceptions.UserException
import java.time.LocalDate

@Entity
class Promise(@Column(length = 255) var action: String = "") {

    @Id
    @GeneratedValue
    var id: Long? = null

    var date: LocalDate = LocalDate.now()

    fun validate() {
        if (action.trim() == "") {
            throw UserException("Promise action is required")
        }
    }

}
