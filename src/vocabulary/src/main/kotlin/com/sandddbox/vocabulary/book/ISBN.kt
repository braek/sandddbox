package com.sandddbox.vocabulary.book

import com.sandddbox.vocabulary.sanitizeSingleLineString

class ISBN private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString()
        val regex = "^97([89])\\d{10}$".toRegex()
        require(regex.matches(sanitized)) {
            "Cannot create ${javaClass.simpleName} from this string: '$str'"
        }
        this.value = sanitized
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ISBN
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object Factory {
        fun create(str: String) = ISBN(str)
    }
}