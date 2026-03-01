package com.sandddbox.vocabulary.search

import com.sandddbox.vocabulary.sanitizeMultiLineString

class Comment private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeMultiLineString()
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
        other as Comment
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object Factory {
        fun create(str: String) = Comment(str)
    }
}