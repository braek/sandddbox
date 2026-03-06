package com.sandddbox.vocabulary.network

sealed interface Seed {

    override fun toString(): String

    fun compressed(): String

    fun expanded(): String

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}