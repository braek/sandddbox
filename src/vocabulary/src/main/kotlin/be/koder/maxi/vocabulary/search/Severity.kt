package be.koder.maxi.vocabulary.search

import be.koder.maxi.vocabulary.sanitizeSingleLineString

/**
 * This class is a candidate to become an enumeration in the future,
 * but for now it takes a custom value instead of fixed values.
 */
class Severity private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString()
        require(sanitized.isNotEmpty()) {
            "Cannot create Severity from empty string"
        }
        this.value = sanitized
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Severity
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object Factory {
        fun create(str: String) = Severity(str)
    }
}