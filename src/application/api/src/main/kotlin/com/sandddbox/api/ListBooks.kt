package com.sandddbox.api

import com.sandddbox.vocabulary.dto.BookListItem

fun interface ListBooks {
    fun listBooks(): List<BookListItem>
}