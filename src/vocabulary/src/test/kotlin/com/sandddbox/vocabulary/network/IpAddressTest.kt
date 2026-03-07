package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@DisplayName("Given a value object that represents an IP address")
class IpAddressTest {

    @ParameterizedTest
    @MethodSource("validIpAddresses")
    fun testValidIpAddress(input: String, expanded: String, compressed: String, ipVersion: Int) {
        val ipAddress = IpAddress.create(input)
        assertThat(ipAddress.expanded()).isEqualTo(expanded)
        assertThat(ipAddress.compressed()).isEqualTo(compressed)
        assertThat(ipAddress.toString()).isEqualTo(compressed)
        assertThat(ipAddress.getIpVersion()).isEqualTo(ipVersion)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "a",
            "fffg::",
            "256.0.0.1",
            "0.256.0.1",
            "0.0.256.1",
            "0.0.1.256",
            ":123:",
        ]
    )
    fun testInvalidIpAddress(input: String) {
        assertThrows<IllegalArgumentException> { IpAddress.create(input) }
    }

    companion object {
        @JvmStatic
        fun validIpAddresses(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("127.0.0.1", "127.0.0.1", "127.0.0.1", 4),
                Arguments.of("\t\r0.0.0.0\n\n", "0.0.0.0", "0.0.0.0", 4),
                Arguments.of("\t\r1.1.1.1\n\n", "1.1.1.1", "1.1.1.1", 4),
                Arguments.of("\t\r255.255.255.255\n\n", "255.255.255.255", "255.255.255.255", 4),
                Arguments.of("::", "0000:0000:0000:0000:0000:0000:0000:0000", "::", 6),
                Arguments.of("a::b", "000a:0000:0000:0000:0000:0000:0000:000b", "a::b", 6),
                Arguments.of("123::", "0123:0000:0000:0000:0000:0000:0000:0000", "123::", 6),
            )
        }
    }
}