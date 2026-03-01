package com.sandddbox.domain.core

import com.sandddbox.domain.core.event.Event

fun interface EventPublisher {

    fun publish(event: Event)

    fun publish(events: List<Event>) {
        events.forEach { publish(it) }
    }
}