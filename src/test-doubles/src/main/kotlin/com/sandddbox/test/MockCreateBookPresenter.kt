package com.sandddbox.test

import com.sandddbox.api.CreateBookPresenter
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.ISBN

class MockCreateBookPresenter : CreateBookPresenter {

    private lateinit var bookId: BookId

    override fun created(bookId: BookId) {
        this.bookId = bookId
    }

    override fun isbnAlreadyInUse(isbn: ISBN) {
        // Do nothing
    }

    override fun noAuthorsProvided() {
        // Do nothing
    }

    fun getBookId() = bookId
}