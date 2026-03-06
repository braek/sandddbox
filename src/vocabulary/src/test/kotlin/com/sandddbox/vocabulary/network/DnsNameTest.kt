package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("Given a value object that represents a DNS name")
class DnsNameTest : ArgumentsProvider {

    @ParameterizedTest
    @MethodSource("validDnsNames")
    fun testValidDnsName(input: String, sanitized: String, dnsLabels: Int) {
        val dnsName = DnsName.create(input)
        assertThat(dnsName.toString()).isEqualTo(sanitized)
        assertThat(dnsName.getDnsLabels().size).isEqualTo(dnsLabels)
    }

    @ParameterizedTest
    @MethodSource("invalidDnsNames")
    fun testInvalidDnsName(input: String) {
        assertThatThrownBy { DnsName.create(input) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    companion object {

        @JvmStatic
        fun validDnsNames(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("WWW.GOOGLE.COM", "www.google.com", 3),
                Arguments.of("\r\n\twww.TWEAKERS.net\r\n\t", "www.tweakers.net", 3),
                Arguments.of("VLAANDEREN.BE", "vlaanderen.be", 2),
                Arguments.of("www.test.com", "www.test.com", 3),
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
                Arguments.of("bla.*.bla"),
                Arguments.of("bla.bla.bla.*"),
                Arguments.of("*.*.google.com"),
                Arguments.of("bla.-bla.bla"),
                Arguments.of("bla.bla-.bla"),
                Arguments.of("aaaaaaaaaa.bbbbbbbbb.ccccccccc.ddddddddd.eeeeeeeee.fffffffff.ggggggggg.hhhhhhhhh.iiiiiiiii.jjjjjjjjj.kkkkkkkkk.lllllllll.mmmmmmmmm.nnnnnnnnn.ooooooooo.ppppppppp.qqqqqqqqq.rrrrrrrrr.sssssssss.ttttttttt.uuuuuuuu.vvvvvvvv.wwwwwwww.xxxxxxxx.yyyyyyyy.zzzzzzzz"),
            )
        }
    }
}