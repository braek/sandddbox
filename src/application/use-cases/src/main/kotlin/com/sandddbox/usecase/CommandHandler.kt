package com.sandddbox.usecase

import com.sandddbox.usecase.command.Command

fun interface CommandHandler {
    fun handle(command: Command)
}