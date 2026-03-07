package com.sandddbox.vocabulary.network

import com.sandddbox.vocabulary.sanitizeSingleLineString
import inet.ipaddr.IPAddress
import inet.ipaddr.IPAddressString
import inet.ipaddr.IPAddressStringParameters

class IpAddress private constructor(str: String) : Comparable<IpAddress> {
    /**
     * Internally represented by an IPAddress, which is basically a CIDR.
     * The subnet value is always set to the maximum value.
     */
    private val value: IPAddress

    init {
        val sanitized = sanitize(str)
        if (isValidIPv4Address(sanitized)) {
            this.value = this.createIPv4Address(sanitized)
        } else if (isIPv6(sanitized)) {
            this.value = this.createIPv6Address(sanitized)
        } else {
            throw IllegalArgumentException("Cannot create ${javaClass.simpleName} from string: '$sanitized'")
        }
    }

    private fun createIPv4Address(str: String): IPAddress {
        val sections = str.split(DOT)
        val ipv4address = "${sections[0].toInt()}.${sections[1].toInt()}.${sections[2].toInt()}.${sections[3].toInt()}"
        val cidr = "$ipv4address/32"
        val params = IPAddressStringParameters.Builder()
            .allowAll(false)
            .allowEmpty(false)
            .allowIPv4(true)
            .allowIPv6(false)
            .allowMask(false)
            .allowPrefix(true)
            .allowPrefixOnly(false)
            .allowSingleSegment(false)
            .allowWildcardedSeparator(false)
            .allow_inet_aton(false)
            .setEmptyAsLoopback(false)
            .toParams()
        /**
         * No try/catch block here: we are pretty sure that the IP address is valid at this point.
         */
        return IPAddressString(cidr, params).toAddress(IPAddress.IPVersion.IPV4)
    }

    private fun createIPv6Address(str: String): IPAddress {
        val sanitized = "$str/128"
        val params = IPAddressStringParameters.Builder()
            .allowAll(false)
            .allowEmpty(false)
            .allowIPv4(false)
            .allowIPv6(true)
            .allowMask(false)
            .allowPrefix(true)
            .allowPrefixOnly(false)
            .allowSingleSegment(false)
            .allowWildcardedSeparator(false)
            .allow_inet_aton(false)
            .setEmptyAsLoopback(false)
            .toParams()
        try {
            val addr = IPAddressString(sanitized, params).toAddress(IPAddress.IPVersion.IPV6)
            val prefixBlock = addr.toPrefixBlock()
            require(addr == prefixBlock) {
                "Cannot create ${javaClass.simpleName} from string: '$sanitized' - the address does not match the prefix block"
            }
            return addr
        } catch (ex: Exception) {
            throw IllegalArgumentException("Cannot create ${javaClass.simpleName} from string: '$sanitized' ($ex)")
        }
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    fun compressed(): String {
        return value.toCompressedString().dropSubnet()
    }

    fun expanded(): String {
        return when (value.ipVersion!!) {
            // Expanded and compressed are the same for IPv4 CIDRs (decimal notation)
            IPAddress.IPVersion.IPV4 -> compressed()
            // Show the full value of an IPv6 CIDR
            IPAddress.IPVersion.IPV6 -> value.toFullString().dropSubnet()
        }
    }

    fun getVersion(): Int {
        return when (value.ipVersion!!) {
            IPAddress.IPVersion.IPV4 -> 4
            IPAddress.IPVersion.IPV6 -> 6
        }
    }

    private fun String.dropSubnet(): String {
        return this.split(SLASH).first()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as IpAddress
        return value == other.value
    }

    override fun toString(): String {
        return compressed()
    }

    override fun compareTo(other: IpAddress): Int {
        /**
         * Sorting should be done by the numbers, so expanded version here please for IPv6.
         */
        return expanded().compareTo(other.expanded())
    }

    companion object {

        private const val COLON = ':'
        private const val SLASH = '/'
        private const val DOT = '.'

        fun create(str: String): IpAddress {
            return IpAddress(sanitize(str))
        }

        private fun isValidIPv4Address(str: String): Boolean {
            val sanitized = sanitize(str)
            val ipv4regex = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$".toRegex()
            if (sanitized.matches(ipv4regex)) {
                val sections = sanitized.split(DOT)
                val sec1 = sections[0].toInt()
                val sec2 = sections[1].toInt()
                val sec3 = sections[2].toInt()
                val sec4 = sections[3].toInt()
                val sectionMaxValue = 255
                return sec1 <= sectionMaxValue && sec2 <= sectionMaxValue && sec3 <= sectionMaxValue && sec4 <= sectionMaxValue
            }
            return false
        }

        private fun isIPv6(str: String): Boolean {
            return sanitize(str).contains(COLON)
        }

        private fun sanitize(str: String): String {
            return str.sanitizeSingleLineString().lowercase()
        }
    }
}