package com.sandddbox.vocabulary.book

import com.sandddbox.vocabulary.sanitizeSingleLineString

class Title private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString()
        require(sanitized.isNotEmpty()) {
            "Cannot create ${javaClass.simpleName} from empty string"
        }
        this.value = sanitized
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Title
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object Factory {
        fun create(str: String) = Title(str)
    }
}