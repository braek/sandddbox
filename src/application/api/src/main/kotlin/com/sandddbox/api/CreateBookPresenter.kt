package com.sandddbox.api

import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.ISBN

interface CreateBookPresenter {

    fun created(bookId: BookId)

    fun isbnAlreadyInUse(isbn: ISBN)

    fun noAuthorsProvided()
}