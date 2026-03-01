package com.sandddbox.usecase

import com.sandddbox.usecase.command.Command

fun interface UseCase<COMMAND : Command, PRESENTER> {
    fun execute(command: COMMAND, presenter: PRESENTER)
}