package be.koder.maxi.vocabulary.network

import be.koder.maxi.vocabulary.sanitizeSingleLineString

class DnsLabel private constructor(str: String) {

    private val value: String

    init {
        val sanitized = str.sanitizeSingleLineString().lowercase()
        require(REGEXES.map { it.matches(sanitized) }.contains(true)) {
            "Cannot create DNS label from string: '$str'"
        }
        this.value = sanitized
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
        /**
         * Accepted regular expressions for DNS labels
         */
        private val REGEXES = setOf(
            /**
             * Single wildcards are possible
             */
            "^\\*$".toRegex(),
            /**
             * Regular host label: minimum 1 character, maximum 63 characters and cannot start or end with a hyphen
             */
            "^(?!-)[a-z-0-9]{1,63}(?<!-)$".toRegex(),
            /**
             * Host label starting with an underscore
             */
            "^_(?!-)[a-z-0-9]{1,62}(?<!-)$".toRegex(),
        )

        fun create(str: String) = DnsLabel(str)
    }
}