package com.sandddbox.inmemory

import com.sandddbox.domain.book.Book
import com.sandddbox.domain.book.BookRepository
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.ISBN

class InMemoryBookRepository : BookRepository, ISBNService {

    private val data = mutableMapOf<BookId, BookSnapshot>()

    override fun getById(id: BookId): Book? {
        if (data.containsKey(id)) {
            return Book.fromSnapshot(data[id]!!)
        }
        return null
    }

    override fun save(aggregateRoot: Book) {
        data[aggregateRoot.getId()] = aggregateRoot.takeSnapshot()
    }

    override fun exists(isbn: ISBN): Boolean {
        return data.map { it.value.isbn }.contains(isbn)
    }

    fun clear() {
        data.clear()
    }
}