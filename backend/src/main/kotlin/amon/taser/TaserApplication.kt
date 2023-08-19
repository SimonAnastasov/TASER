package amon.taser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaserApplication

fun main(args: Array<String>) {
    runApplication<TaserApplication>(*args)
}
