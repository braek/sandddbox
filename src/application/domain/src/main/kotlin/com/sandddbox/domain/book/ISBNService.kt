package com.sandddbox.domain.book

import com.sandddbox.vocabulary.book.ISBN

fun interface ISBNService {
    fun exists(isbn: ISBN): Boolean
}