package com.sandddbox.vocabulary.dto

import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

data class BookListItem(
    val isbn: ISBN,
    val title: Title,
)