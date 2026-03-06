package com.sandddbox.vocabulary.network

import com.sandddbox.vocabulary.sanitizeSingleLineString

class DnsName private constructor(str: String) : Seed {

    private val value: String
    private val dnsLabels: List<DnsLabel>

    init {
        /**
         * Sanitize values
         */
        val sanitized = str.sanitizeSingleLineString()
            .removeSuffix(DOT.toString())
            .removePrefix(DOT.toString())
            .lowercase()

        /**
         * Convert and validate DNS labels
         */
        val validDnsLabels = mutableListOf<DnsLabel>()
        sanitized.split(DOT).forEachIndexed { index, rawDnsLabel ->
            run {
                try {
                    validDnsLabels.add(DnsLabel.create(rawDnsLabel))
                } catch (_: IllegalArgumentException) {
                    throw IllegalArgumentException(
                        "Cannot create ${javaClass.simpleName} from string: '$sanitized' because ${DnsLabel::class.java.simpleName} #${index + 1} is invalid: '$rawDnsLabel'"
                    )
                }
            }
        }

        /**
         * At least one DNS label is required
         */
        require(validDnsLabels.isNotEmpty()) {
            "Cannot create ${javaClass.simpleName} from string: '$sanitized' - no DNS labels are present"
        }

        /**
         * Validate the maximum length of the DNS name.
         *
         * We don't include the trailing dot here and also must be aware of the fact that every DNS label gets his own "length byte".
         *
         * That makes the maximum length 253 characters for the DNS name how we represent it.
         */
        val valueToBe = validDnsLabels.joinToString(separator = DOT.toString()).lowercase()
        require(valueToBe.length <= 253) {
            "Cannot create ${javaClass.simpleName} from string: '$sanitized' - exceeds maximum length of 253 characters"
        }

        /**
         * Check the optional wildcard in the DNS name.
         *
         * Only the first DNS label can be a wildcard, and there need to be at least 3 DNS labels in total (the first one being the wildcard).
         */
        val wildcards = validDnsLabels.map { it.isWildcard() }.count { it }
        require(wildcards == 0 || (wildcards == 1 && validDnsLabels.size > 2 && validDnsLabels.first().isWildcard())) {
            "Cannot create ${javaClass.simpleName} from string: '$sanitized' - invalid use of wildcard"
        }

        /**
         * Assign internal values
         */
        this.value = valueToBe
        this.dnsLabels = validDnsLabels.toList()
    }

    fun getDnsLabels(): List<DnsLabel> = dnsLabels

    fun isWildcardDomain(): Boolean = dnsLabels.first().isWildcard()

    fun isRootDomain(): Boolean = !isWildcardDomain() && dnsLabels.size == 2

    override fun toString(): String {
        return value
    }

    override fun compressed(): String {
        return toString()
    }

    override fun expanded(): String {
        return toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DnsName
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        /**
         * Constants
         */
        private const val DOT = '.'
        /**
         * Factory method
         */
        fun create(str: String) = DnsName(str)
    }
}