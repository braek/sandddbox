package com.sandddbox.test

import com.sandddbox.domain.core.EventPublisher
import com.sandddbox.domain.core.event.Event
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration

class MockEventPublisher : EventPublisher {

    private val publishedEvents = mutableListOf<Event>()

    override fun publish(event: Event) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }

    fun verifyNoEventsPublished() {
        assertThat(publishedEvents).isEmpty()
    }

    fun verifyLastPublishedEvents(expectedEvents: List<Event>, ignoredFields: Set<String> = emptySet()) {
        require(expectedEvents.isNotEmpty()) { "Expected events cannot be empty" }
        val config = RecursiveComparisonConfiguration.builder()
            .withStrictTypeChecking(true)
            .withIgnoredFields(*(ignoredFields + "id" + "timestamp").toSet().toTypedArray())
            .build()
        assertThat(publishedEvents.takeLast(expectedEvents.size).toList())
            .usingRecursiveComparison(config)
            .isEqualTo(expectedEvents.toList())
    }
}