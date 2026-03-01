package com.sandddbox.vocabulary.time

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class ZuluTimestamp private constructor(instant: Instant) : Comparable<ZuluTimestamp> {

    private val value: Instant = instant.atOffset(ZoneOffset.UTC)
        .toInstant()
        .truncatedTo(ChronoUnit.MICROS)

    override fun compareTo(other: ZuluTimestamp): Int {
        return value.compareTo(other.value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ZuluTimestamp
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

    fun toInstant(): Instant {
        return value
    }

    fun toOffsetDateTime(): OffsetDateTime {
        return value.atOffset(ZoneOffset.UTC)
    }

    companion object {
        fun now(): ZuluTimestamp {
            return ZuluTimestamp(Instant.now())
        }
    }
}