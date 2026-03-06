package com.sandddbox.vocabulary.network

import com.sandddbox.vocabulary.sanitizeSingleLineString

class DnsLabel private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString().lowercase()
        require(WILDCARD_REGEX.matches(sanitized) || REGULAR_DNS_LABEL_REGEX.matches(sanitized) || UNDERSCORED_DNS_LABEL_REGEX.matches(sanitized)) {
            "Cannot create ${javaClass.simpleName} from this string: '$str'"
        }
        this.value = sanitized
    }

    fun isWildcard(): Boolean = WILDCARD_REGEX.matches(value)

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DnsLabel
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Regular expressions for the types of DNS labels
         */
        private val WILDCARD_REGEX = "^\\*$".toRegex()
        private val REGULAR_DNS_LABEL_REGEX = "^(?!-)[a-z-0-9]{1,63}(?<!-)$".toRegex()
        private val UNDERSCORED_DNS_LABEL_REGEX = "^_(?!-)[a-z-0-9]{1,62}(?<!-)$".toRegex()

        /**
         * Factory method
         */
        fun create(str: String) = DnsLabel(str)
    }
}