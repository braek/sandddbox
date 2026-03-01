package com.sandddbox.domain.book

import com.sandddbox.domain.core.Version
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

/**
 * Immutable version of the Book aggregate.
 *
 * This is called the "memento pattern".
 */
data class BookSnapshot(
    val id: BookId,
    val isbn: ISBN,
    val title: Title,
    val description: Description,
    val authors: Set<Author>,
    val version: Version
)