package com.sandddbox.test

import com.sandddbox.domain.book.Book
import com.sandddbox.domain.book.BookRepository
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.ISBN
import org.assertj.core.api.Assertions.assertThat
import java.util.concurrent.ConcurrentHashMap

class MockBookRepository : BookRepository, ISBNService {

    private val data = ConcurrentHashMap<BookId, BookSnapshot>()

    override fun getById(id: BookId): Book? {
        return data[id]?.let { Book.fromSnapshot(it) }
    }

    override fun save(aggregateRoot: Book) {
        val snapshot = aggregateRoot.takeSnapshot()
        data.compute(snapshot.id) { _, existing ->
            if (existing != null && existing.version != snapshot.version) {
                throw IllegalStateException("Optimistic locking failed for book with id: ${snapshot.id}")
            }
            snapshot.copy(version = snapshot.version.increment())
        }
    }

    override fun exists(isbn: ISBN): Boolean {
        return data.values.any { it.isbn == isbn }
    }

    fun verifySize(size: Int) {
        assertThat(data.size).isEqualTo(size)
    }

    fun verifyEmpty() {
        verifySize(0)
    }

    fun clear() {
        data.clear()
    }
}