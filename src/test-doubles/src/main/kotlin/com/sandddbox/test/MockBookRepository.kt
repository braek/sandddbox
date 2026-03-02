package com.sandddbox.test

import com.sandddbox.domain.book.Book
import com.sandddbox.domain.book.BookRepository
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.ISBN
import org.assertj.core.api.Assertions.assertThat

class MockBookRepository : BookRepository, ISBNService {

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

    fun verifySize(size: Int) {
        assertThat(data.size).isEqualTo(size)
    }

    fun clear() {
        data.clear()
    }
}