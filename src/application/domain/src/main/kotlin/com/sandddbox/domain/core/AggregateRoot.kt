package com.sandddbox.domain.core

import com.sandddbox.domain.core.event.Event
import com.sandddbox.vocabulary.aggregate.AggregateRootId

abstract class AggregateRoot<AGGREGATE_ROOT_ID : AggregateRootId> {

    private val enqueuedEvents: MutableList<Event> = mutableListOf()

    abstract fun getId(): AGGREGATE_ROOT_ID

    protected fun enqueue(event: Event) {
        this.enqueuedEvents.add(event)
    }

    fun getQueuedEvents(): List<Event> {
        return enqueuedEvents.toList()
    }
}