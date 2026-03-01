package com.sandddbox.usecase.book

import com.sandddbox.api.CreateBookPresenter
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.domain.core.Version
import com.sandddbox.inmemory.InMemoryBookRepository
import com.sandddbox.inmemory.InMemoryEventPublisher
import com.sandddbox.test.fail
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to create a Book")
class CreateBookUseCaseTest {

    private val eventPublisher = InMemoryEventPublisher()
    private val bookRepository: InMemoryBookRepository = InMemoryBookRepository()
    private val isbnService: ISBNService = bookRepository
    private val useCase = CreateBookUseCase(isbnService, bookRepository, eventPublisher)

    @BeforeEach
    fun setUp() {
        eventPublisher.clear()
        bookRepository.clear()
    }

    @Nested
    @DisplayName("when Happy Flow")
    inner class TestHappyFlow : CreateBookPresenter {

        private var createdCalled = false
        private lateinit var bookId: BookId

        private val isbn = ISBN.create("9780123456789")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Clean Code is a book about writing clean code")
        private val author = Author.create("Kristof Verbraeken")

        @BeforeEach
        fun setUp() {
            useCase.createBook(
                isbn,
                title,
                description,
                setOf(author),
                this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertTrue(createdCalled)
        }

        @Test
        fun `it should be saved`() {
            val bookSnapshot = bookRepository.getById(bookId)?.takeSnapshot()
            assertThat(bookSnapshot).isNotNull()
            assertThat(bookSnapshot).isEqualTo(BookSnapshot(
                bookId,
                isbn,
                title,
                description,
                setOf(author),
                Version.initial()
            ))
        }

        @Test
        fun `it should publish an event`() {
            /**
             * TODO: complete this test
             */
            fail()
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
}