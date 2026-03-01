package com.sandddbox.inmemory

import com.sandddbox.domain.core.EventPublisher
import com.sandddbox.domain.core.event.Event

class InMemoryEventPublisher : EventPublisher {

    private val publishedEvents = mutableListOf<Event>()

    override fun publish(event: Event) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}