package com.sandddbox.vocabulary.aggregate

/**
 * Marker interface for aggregate root IDs
 */
sealed interface AggregateRootId {

    override fun toString(): String

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}