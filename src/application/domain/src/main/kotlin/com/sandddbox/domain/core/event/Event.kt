package com.sandddbox.domain.core.event

import com.sandddbox.domain.core.EventId
import com.sandddbox.vocabulary.aggregate.AggregateRootId
import com.sandddbox.vocabulary.time.ZuluTimestamp

sealed interface Event {
    val id: EventId
    val timestamp: ZuluTimestamp
    val streamId: AggregateRootId
}