package com.sandddbox.domain.core

data class Version(val value: Int) {
    fun increment(): Version = Version(value + 1)
    companion object {
        fun initial(): Version = Version(0)
    }
}