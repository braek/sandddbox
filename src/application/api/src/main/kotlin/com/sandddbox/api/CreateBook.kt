package com.sandddbox.api

import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

fun interface CreateBook {
    fun createBook(isbn: ISBN, title: Title, description: Description, authors: Set<Author>, presenter: CreateBookPresenter)
}