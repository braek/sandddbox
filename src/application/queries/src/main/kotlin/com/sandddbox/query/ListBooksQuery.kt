package com.sandddbox.query

import com.sandddbox.api.ListBooks
import com.sandddbox.query.model.ListBooksModel
import com.sandddbox.vocabulary.dto.BookListItem

data class ListBooksQuery(val model: ListBooksModel) : ListBooks {
    override fun listBooks(): List<BookListItem> {
        return model.listBooks()
    }
}