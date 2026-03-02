package com.sandddbox.usecase

import com.sandddbox.usecase.command.Command

fun interface CommandPublisher {
    fun publish(command: Command)
}