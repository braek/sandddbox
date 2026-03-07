package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("Given a value object that represents a DNS name")
class DnsNameTest {

    @ParameterizedTest
    @MethodSource("validDnsNames")
    fun testValidDnsName(input: String, sanitized: String, dnsLabels: Int, isWildcardDomain: Boolean, wireFormat: String) {
        val dnsName = DnsName.create(input)
        assertThat(dnsName.toString()).isEqualTo(sanitized)
        assertThat(dnsName.getDnsLabels().size).isEqualTo(dnsLabels)
        assertThat(dnsName.isWildcardDomain()).isEqualTo(isWildcardDomain)
        assertThat(dnsName.wireFormat()).isEqualTo(wireFormat)
    }

    @ParameterizedTest
    @MethodSource("invalidDnsNames")
    fun testInvalidDnsName(input: String) {
        assertThrows<IllegalArgumentException> { DnsName.create(input) }
    }

    companion object {

        @JvmStatic
        fun validDnsNames(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("WWW.GOOGLE.COM", "www.google.com", 3, false, "[3]www [6]google [3]com [0]"),
                Arguments.of("\r\n\twww.TWEAKERS.net\r\n\t", "www.tweakers.net", 3, false, "[3]www [8]tweakers [3]net [0]"),
                Arguments.of("VLAANDEREN.BE", "vlaanderen.be", 2, false, "[10]vlaanderen [2]be [0]"),
                Arguments.of("666.museum", "666.museum", 2, false, "[3]666 [6]museum [0]"),
                Arguments.of("www.test.com", "www.test.com", 3, false, "[3]www [4]test [3]com [0]"),
                Arguments.of("*.wildcard-domain.com", "*.wildcard-domain.com", 3, true, "[1]* [15]wildcard-domain [3]com [0]"),
                Arguments.of("*.SUPERMAN.COM", "*.superman.com", 3, true, "[1]* [8]superman [3]com [0]"),
                Arguments.of("\r\n\t_dmarc.test.com\r\n\t", "_dmarc.test.com", 3, false, "[6]_dmarc [4]test [3]com [0]"),
                Arguments.of("_acme-challenge.sub.example.com", "_acme-challenge.sub.example.com", 4, false, "[15]_acme-challenge [3]sub [7]example [3]com [0]"),
                Arguments.of(
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb.ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc.ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.e",
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb.ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc.ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd.e",
                    5,
                    false,
                    "[63]aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa [63]bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb [63]ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc [59]ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd [1]e [0]"
                ),
            )
        }

        @JvmStatic
        fun invalidDnsNames(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("*"),
                Arguments.of(""),
                Arguments.of("batman@gothamcity.com"),
                Arguments.of("spidermand@nyc.com"),
                Arguments.of("###"),
                Arguments.of("*.*.*.com"),
                Arguments.of("bla.*.bla"),
                Arguments.of("bla.bla.bla.*"),
                Arguments.of("*.*.google.com"),
                Arguments.of("www.*.google.com"),
                Arguments.of("*.*.*.hln.be"),
                Arguments.of("bla.-bla.bla"),
                Arguments.of("bla.bla-.bla"),
                Arguments.of("aaaaaaaaaa.bbbbbbbbb.ccccccccc.ddddddddd.eeeeeeeee.fffffffff.ggggggggg.hhhhhhhhh.iiiiiiiii.jjjjjjjjj.kkkkkkkkk.lllllllll.mmmmmmmmm.nnnnnnnnn.ooooooooo.ppppppppp.qqqqqqqqq.rrrrrrrrr.sssssssss.ttttttttt.uuuuuuuu.vvvvvvvv.wwwwwwww.xxxxxxxx.yyyyyyyy.zzzzzzzz"),
            )
        }
    }
}