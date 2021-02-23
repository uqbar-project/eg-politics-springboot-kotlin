package org.uqbar.politics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PoliticsApplication

fun main(args: Array<String>) {
    runApplication<PoliticsApplication>(*args)
}