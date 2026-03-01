package com.sandddbox.usecase.command

import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

data class CreateBookCommand(
    val isbn: ISBN,
    val title: Title,
    val description: Description,
    val authors: Set<Author>
) : Command