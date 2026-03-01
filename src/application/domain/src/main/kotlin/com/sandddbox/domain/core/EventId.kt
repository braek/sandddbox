package com.sandddbox.domain.core

import java.util.UUID

data class EventId(val value: UUID) {
    companion object {
        fun generate(): EventId = EventId(UUID.randomUUID())
    }
}