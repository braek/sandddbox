package com.sandddbox.vocabulary.network

class Port private constructor(value: Int) : Comparable<Port> {

    private val value: Int

    init {
        require(value in 0..65535) {
            "Cannot create ${javaClass.simpleName} from integer: '$value' - must be in the range 0 to 65535"
        }
        this.value = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Port
        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun compareTo(other: Port): Int {
        return value.compareTo(other.value)
    }

    fun value(): Int = value

    companion object {
        fun create(value: Int) = Port(value)
    }
}