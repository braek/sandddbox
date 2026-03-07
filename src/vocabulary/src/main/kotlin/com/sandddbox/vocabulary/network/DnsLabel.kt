package com.sandddbox.vocabulary.network

import com.sandddbox.vocabulary.sanitizeSingleLineString

class DnsLabel private constructor(str: String) {

    private val value: String

    init {
        val sanitizedValue = str.sanitizeSingleLineString().lowercase()
        require(REGEX_WILDCARD.matches(sanitizedValue) || REGEX_DNS_LABEL.matches(sanitizedValue) || REGEX_DNS_LABEL_WITH_UNDERSCORE.matches(sanitizedValue)) {
            "Cannot create ${javaClass.simpleName} from this string: '$sanitizedValue'"
        }
        this.value = sanitizedValue
    }

    fun isWildcard(): Boolean = REGEX_WILDCARD.matches(value)

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
        private val REGEX_WILDCARD = "^\\*$".toRegex()
        private val REGEX_DNS_LABEL = "^(?!-)[a-z-0-9]{1,63}(?<!-)$".toRegex()
        private val REGEX_DNS_LABEL_WITH_UNDERSCORE = "^_(?!-)[a-z-0-9]{1,62}(?<!-)$".toRegex()
        /**
         * Factory method
         */
        fun create(str: String) = DnsLabel(str)
    }
}