package org.uqbar.politics.domain

import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.uqbar.politics.domain.exceptions.UserException
import org.uqbar.politics.serializers.View

@Entity
class Candidate {
    @Id
    @GeneratedValue
    @JsonView(View.Zone.Detail::class)
    var id: Long? = null

    @JsonView(View.Zone.Detail::class)
    var name = ""

    @ManyToOne
    @JsonView(View.Zone.Detail::class)
    var party: Party? = null

    @JsonView(View.Zone.Detail::class)
    var votes = 0

    @OneToMany(cascade = [CascadeType.ALL])
    @OrderColumn
    var promises = mutableListOf<Promise>()

    @ElementCollection
    @OrderColumn
    var opinions = mutableListOf<String>()

    fun validate() {
        if (name.trim() == "") {
            throw UserException("Name is required")
        }
        if (party === null) {
            throw UserException("Candidate must belong to a political party")
        }
    }

    override fun toString(): String = name

    fun addPromise(newPromise: String) {
        promises.add(Promise(newPromise))
    }

    fun updatePromises(newPromises: List<Promise>) {
        if (newPromises.isNotEmpty()) {
            promises.clear()
            promises.addAll(newPromises.map {promise -> Promise(promise.action) })
        }
    }

    fun addOpinion(opinion: String) {
        opinions.add(opinion)
    }

    fun vote() {
        votes++
    }

    fun reset() {
        promises = mutableListOf()
        opinions = mutableListOf()
    }
}