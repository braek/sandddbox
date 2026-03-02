package com.sandddbox.domain.core

import com.sandddbox.domain.core.event.Event

fun interface EventHandler {
    fun handle(event: Event)
}