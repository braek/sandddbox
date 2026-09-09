package com.sandddbox.vocabulary.dto

import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

data class BookDetails(
    val isbn: ISBN,
    val title: Title,
    val description: Description,
    val authors: Set<Author>,
)