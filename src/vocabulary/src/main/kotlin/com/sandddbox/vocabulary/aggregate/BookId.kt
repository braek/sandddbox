package com.sandddbox.vocabulary.aggregate

import java.util.*

data class BookId(val value: UUID) : AggregateRootId {

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BookId
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        fun generate(): BookId = BookId(UUID.randomUUID())
    }
}