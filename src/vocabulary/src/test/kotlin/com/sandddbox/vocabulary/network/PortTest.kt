package com.sandddbox.vocabulary.network

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Given a value object that represents a Port")
class PortTest {

    @ParameterizedTest
    @ValueSource(ints = [
        0,
        1,
        2,
        80,
        8080,
        123,
        666,
        10001,
        20202,
        65535,
    ])
    fun testValidPort(input: Int) {
        val port = Port.create(input)
        assertThat(port.value()).isEqualTo(input)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 65536, 100000])
    fun testInvalidPort(input: Int) {
        assertThatThrownBy { Port.create(input) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}