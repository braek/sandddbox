package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@DisplayName("Given a value object that represents a DNS label")
class DnsLabelTest {

    @ParameterizedTest
    @MethodSource("validDnsLabels")
    fun testValidDnsLabel(input: String, sanitized: String, isWildcard: Boolean) {
        val dnsLabel = DnsLabel.create(input)
        assertThat(dnsLabel.toString()).isEqualTo(sanitized)
        assertThat(dnsLabel.isWildcard()).isEqualTo(isWildcard)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "_",
            "-abc",
            "abc-",
            "202cb962ac59075b964b07152d234b70202cb962ac59075b964b07152d234b70",
        ]
    )
    fun testInvalidDnsLabel(input: String) {
        assertThatThrownBy { DnsLabel.create(input) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    companion object {
        @JvmStatic
        fun validDnsLabels(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("ORG", "org", false),
                Arguments.of("MUSEUM", "museum", false),
                Arguments.of("\n\n\nB\n\rE\t\t\t\n", "be", false),
                Arguments.of("\t\r\n666\t\r\n", "666", false),
                Arguments.of("xn--mnchen-3ya", "xn--mnchen-3ya", false),
                Arguments.of("_DMARC", "_dmarc", false),
                Arguments.of("_ACME-challenge", "_acme-challenge", false),
                Arguments.of("\t\r\n*\t\r\n", "*", true),
                Arguments.of("123", "123", false),
                Arguments.of("ABC456", "abc456", false),
            )
        }
    }
}