package com.sandddbox.query.model

import com.sandddbox.vocabulary.dto.BookListItem

fun interface ListBooksModel {
    fun listBooks(): List<BookListItem>
}