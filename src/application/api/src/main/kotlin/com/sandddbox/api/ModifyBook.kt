package com.sandddbox.api

import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.Title

fun interface ModifyBook {
    fun modifyBook(bookId: BookId, title: Title, description: Description, authors: Set<Author>, presenter: ModifyBookPresenter)
}