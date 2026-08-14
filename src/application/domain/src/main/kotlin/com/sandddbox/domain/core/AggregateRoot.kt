package com.sandddbox.domain.core

import com.sandddbox.domain.core.event.Event
import com.sandddbox.vocabulary.aggregate.AggregateRootId

abstract class AggregateRoot<ID : AggregateRootId> {

    private val outbox: MutableList<Event> = mutableListOf()

    abstract fun getId(): ID

    protected fun enqueue(event: Event) {
        this.outbox.add(event)
    }

    fun getOutbox(): List<Event> {
        return outbox.toList()
    }
}