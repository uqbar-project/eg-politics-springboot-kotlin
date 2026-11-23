package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonView
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
    JsonSubTypes.Type(value = PeronistParty::class, name = "PJ"),
    JsonSubTypes.Type(value = PreservativeParty::class, name = "PRE")
)
@Inheritance(strategy=InheritanceType.JOINED)
abstract class Party {

    @Id
    @GeneratedValue
    @JsonView(View.Zone.Detail::class)
    open var id: Long? = null

    @JsonView(View.Zone.Detail::class)
    open lateinit var name: String

    open var members: Int = 0

    open fun validate() {
        if (name.trim() == "") {
            throw UserException("Name is required")
        }
        if (members < 1000) {
            throw UserException("Party must have at least 1000 members")
        }
    }

    override fun toString(): String = name

}

@Entity
class PeronistParty : Party() {
    var populist = false
}

@Entity
class PreservativeParty : Party() {
    var foundationDate: LocalDate = LocalDate.now()

    override fun validate() {
        super.validate()
        if (foundationDate.isAfter(LocalDate.now())) {
            throw UserException("La date de creación debe ser anterior a la de hoy")
        }
    }

}
