package com.sandddbox.usecase.command

import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.Title

data class ModifyBookCommand(
    val bookId: BookId,
    val title: Title,
    val description: Description,
    val authors: Set<Author>
) : Command