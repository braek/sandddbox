package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@DisplayName("Given a value object that represents a DNS label")
class DnsLabelTest {

    @ParameterizedTest
    @MethodSource("validDnsLabels")
    fun testValidDnsLabel(input: String, sanitized: String, isWildcard: Boolean, wireFormat: String) {
        val dnsLabel = DnsLabel.create(input)
        assertThat(dnsLabel.toString()).isEqualTo(sanitized)
        assertThat(dnsLabel.isWildcard()).isEqualTo(isWildcard)
        assertThat(dnsLabel.wireFormat()).isEqualTo(wireFormat)
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
        assertThrows<IllegalArgumentException> { DnsLabel.create(input) }
    }

    companion object {
        @JvmStatic
        fun validDnsLabels(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("ORG", "org", false, "[3]org"),
                Arguments.of("MUSEUM", "museum", false, "[6]museum"),
                Arguments.of("\n\n\nB\n\rE\t\t\t\n", "be", false, "[2]be"),
                Arguments.of("\t\r\n666\t\r\n", "666", false, "[3]666"),
                Arguments.of("xn--mnchen-3ya", "xn--mnchen-3ya", false, "[14]xn--mnchen-3ya"),
                Arguments.of("_DMARC", "_dmarc", false, "[6]_dmarc"),
                Arguments.of("_ACME-challenge", "_acme-challenge", false, "[15]_acme-challenge"),
                Arguments.of("\t\r\n*\t\r\n", "*", true, "[1]*"),
                Arguments.of("123", "123", false, "[3]123"),
                Arguments.of("ABC456", "abc456", false, "[6]abc456"),
                Arguments.of("aa--123--bb", "aa--123--bb", false, "[11]aa--123--bb"),
            )
        }
    }
}