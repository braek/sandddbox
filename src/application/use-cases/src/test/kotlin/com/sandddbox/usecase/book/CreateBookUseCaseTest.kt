package com.sandddbox.usecase.book

import com.sandddbox.api.CreateBook
import com.sandddbox.api.CreateBookPresenter
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.domain.core.Version
import com.sandddbox.domain.core.event.BookCreated
import com.sandddbox.test.MockBookRepository
import com.sandddbox.test.MockCreateBookPresenter
import com.sandddbox.test.MockEventPublisher
import com.sandddbox.test.fail
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to create a Book")
class CreateBookUseCaseTest {

    private val eventPublisher = MockEventPublisher()
    private val bookRepository = MockBookRepository()
    private val isbnService: ISBNService = bookRepository
    private val createBook: CreateBook = CreateBookUseCase(isbnService, bookRepository, eventPublisher)

    @BeforeEach
    fun setUp() {
        eventPublisher.clear()
        bookRepository.clear()
    }

    @Nested
    @DisplayName("when Happy Flow")
    inner class TestWhenHappyFlow : CreateBookPresenter {

        private var createdCalled = false
        private lateinit var bookId: BookId

        private val isbn = ISBN.create("9781408856772")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = setOf(
            Author.create("Elvis Presley"),
            Author.create("Joske Vermeulen")
        )

        @BeforeEach
        fun setUp() {
            createBook.createBook(
                isbn = isbn,
                title = title,
                description = description,
                authors = authors,
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(createdCalled).isTrue()
        }

        @Test
        fun `it should be saved`() {
            val bookSnapshot = bookRepository.getById(bookId)?.takeSnapshot()
            assertThat(bookSnapshot).isEqualTo(BookSnapshot(
                bookId,
                isbn,
                title,
                description,
                authors,
                Version.initial().increment()
            ))
        }

        @Test
        fun `it should publish an event`() {
            eventPublisher.verifyLastPublishedEvents(listOf(
                BookCreated(bookId)
            ))
        }

        override fun created(bookId: BookId) {
            createdCalled = true
            this.bookId = bookId
        }

        override fun isbnAlreadyInUse(isbn: ISBN) {
            fail()
        }

        override fun noAuthorsProvided() {
            fail()
        }
    }

    @Nested
    @DisplayName("when ISBN already in use")
    inner class TestWhenISBNAlreadyInUse : CreateBookPresenter {

        private var isbnAlreadyInUseCalled = false

        private val isbn = ISBN.create("9781408856772")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = setOf(
            Author.create("Elvis Presley"),
            Author.create("Joske Vermeulen")
        )

        @BeforeEach
        fun setUp() {
            createBook.createBook(
                isbn = isbn,
                title = title,
                description = description,
                authors = authors,
                presenter = MockCreateBookPresenter()
            )
            eventPublisher.clear()
            createBook.createBook(
                isbn = isbn,
                title = title,
                description = description,
                authors = authors,
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(isbnAlreadyInUseCalled).isTrue()
        }

        @Test
        fun `it should NOT be saved`() {
            bookRepository.verifySize(1)
        }

        @Test
        fun `it should NOT publish an event`() {
            eventPublisher.verifyNoEventsPublished()
        }

        override fun created(bookId: BookId) {
            fail()
        }

        override fun isbnAlreadyInUse(isbn: ISBN) {
            isbnAlreadyInUseCalled = true
        }

        override fun noAuthorsProvided() {
            fail()
        }
    }

    @Nested
    @DisplayName("when no authors are provided")
    inner class TestWhenNoAuthorsProvided : CreateBookPresenter {

        private var noAuthorsProvidedCalled = false

        private val isbn = ISBN.create("9781408856772")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = emptySet<Author>()

        @BeforeEach
        fun setUp() {
            createBook.createBook(
                isbn = isbn,
                title = title,
                description = description,
                authors = authors,
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(noAuthorsProvidedCalled).isTrue()
        }

        @Test
        fun `it should NOT be saved`() {
            bookRepository.verifyEmpty()
        }

        @Test
        fun `it should NOT publish an event`() {
            eventPublisher.verifyNoEventsPublished()
        }

        override fun created(bookId: BookId) {
            fail()
        }

        override fun isbnAlreadyInUse(isbn: ISBN) {
            fail()
        }

        override fun noAuthorsProvided() {
            noAuthorsProvidedCalled = true
        }
    }
}