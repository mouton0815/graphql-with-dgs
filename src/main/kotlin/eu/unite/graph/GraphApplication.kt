package eu.unite.graph

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphApplication

fun main(args: Array<String>) {
    runApplication<GraphApplication>(*args)
}
