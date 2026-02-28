package be.koder.maxi.vocabulary.network

import be.koder.maxi.vocabulary.sanitizeSingleLineString

class DnsLabel private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString().lowercase()
        require(isWildcard(sanitized) || isRegularDnsLabel(sanitized) || isUnderscoredDnsLabel(sanitized)) {
            "Cannot create ${javaClass.simpleName} from invalid string: '$str'"
        }
        this.value = sanitized
    }

    private fun isWildcard(value: String): Boolean {
        return value == "*"
    }

    private fun isRegularDnsLabel(value: String): Boolean {
        return "^(?!-)[a-z-0-9]{1,63}(?<!-)$".toRegex().matches(value)
    }

    private fun isUnderscoredDnsLabel(value: String): Boolean {
        return "^_(?!-)[a-z-0-9]{1,62}(?<!-)$".toRegex().matches(value)
    }

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

    companion object Factory {
        fun create(str: String) = DnsLabel(str)
    }
}