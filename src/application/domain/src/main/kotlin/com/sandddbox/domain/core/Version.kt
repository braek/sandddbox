package com.sandddbox.domain.core

data class Version(val value: Int) {
    companion object {
        fun initial(): Version = Version(0)
    }
}