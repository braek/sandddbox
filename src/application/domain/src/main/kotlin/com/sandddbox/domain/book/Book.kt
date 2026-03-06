package com.sandddbox.domain.book

import com.sandddbox.domain.core.AggregateRoot
import com.sandddbox.domain.core.Version
import com.sandddbox.domain.core.event.BookCreated
import com.sandddbox.domain.core.event.BookModified
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

class Book private constructor(
    private val id: BookId,
    private val isbn: ISBN,
    private var title: Title,
    private var description: Description,
    private val authors: MutableSet<Author>,
    private val version: Version
) : AggregateRoot<BookId>() {

    override fun getId(): BookId {
        return id
    }

    fun takeSnapshot() = BookSnapshot(
        id,
        isbn,
        title,
        description,
        authors.toSet(),
        version
    )

    fun modify(title: Title, description: Description, authors: Set<Author>) {
        this.title = title
        this.description = description
        this.authors.clear()
        this.authors.addAll(authors)
        this.enqueue(BookModified(id))
    }

    companion object {

        fun create(isbn: ISBN, title: Title, description: Description, authors: Set<Author>): Book {
            val book = Book(
                id = BookId.generate(),
                isbn = isbn,
                title = title,
                description = description,
                authors = authors.toMutableSet(),
                version = Version.initial()
            )
            book.enqueue(BookCreated(book.getId()))
            return book
        }

        fun fromSnapshot(snapshot: BookSnapshot): Book {
            return Book(
                id = snapshot.id,
                isbn = snapshot.isbn,
                title = snapshot.title,
                description = snapshot.description,
                authors = snapshot.authors.toMutableSet(),
                version = snapshot.version
            )
        }
    }
}