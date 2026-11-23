package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View

@Entity
class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Zone.Plain::class, View.Zone.Detail::class)
    var id: Long? = null

    @Column(length = 50)
    @JsonView(View.Zone.Plain::class, View.Zone.Detail::class)
    lateinit var name: String

    @OneToMany
    @JsonView(View.Zone.Detail::class)
    lateinit var candidates: MutableSet<Candidate>

    fun validate() {
        if (name.trim() == "") {
            throw UserException("Name is required")
        }
        if (candidates.isEmpty()) {
            throw UserException("Zone must have at least one candidate")
        }
    }

    override fun toString(): String = name
}