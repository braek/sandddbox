package com.sandddbox.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Entry point of the ```rest-adapter``` — the inbound (driving) adapter that exposes
 * the application's use cases and queries over HTTP.
 */
@SpringBootApplication
open class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}