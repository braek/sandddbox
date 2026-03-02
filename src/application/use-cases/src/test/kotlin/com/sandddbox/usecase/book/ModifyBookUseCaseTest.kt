package com.sandddbox.usecase.book

import com.sandddbox.api.ModifyBook
import com.sandddbox.api.ModifyBookPresenter
import com.sandddbox.domain.book.Book
import com.sandddbox.domain.book.BookSnapshot
import com.sandddbox.domain.core.Version
import com.sandddbox.domain.core.event.BookModified
import com.sandddbox.test.MockBookRepository
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

@DisplayName("Given a use case to modify a Book")
class ModifyBookUseCaseTest {

    private val eventPublisher = MockEventPublisher()
    private val bookRepository = MockBookRepository()
    private val modifyBook: ModifyBook = ModifyBookUseCase(bookRepository, eventPublisher)

    @BeforeEach
    fun setUp() {
        eventPublisher.clear()
        bookRepository.clear()
    }

    @Nested
    @DisplayName("when no authors are provided")
    inner class TestWhenNoAuthorsProvided : ModifyBookPresenter {

        private var noAuthorsProvidedCalled = false

        private val bookId = BookId.generate()
        private val isbn = ISBN.create("9781408856772")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = setOf(
            Author.create("Elvis Presley"),
            Author.create("Joske Vermeulen")
        )

        @BeforeEach
        fun setUp() {
            bookRepository.save(
                Book.fromSnapshot(
                    BookSnapshot(
                        id = bookId,
                        isbn = isbn,
                        title = title,
                        description = description,
                        authors = authors,
                        Version.initial()
                    )
                )
            )
            modifyBook.modifyBook(
                bookId = bookId,
                title = title,
                description = description,
                authors = emptySet(),
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(noAuthorsProvidedCalled).isTrue()
        }

        @Test
        fun `it should NOT be saved`() {
            bookRepository.verifySize(1)
        }

        @Test
        fun `it should NOT publish an event`() {
            eventPublisher.verifyNoEventsPublished()
        }

        override fun bookDoesNotExist(bookId: BookId) {
            fail()
        }

        override fun noAuthorsProvided() {
            noAuthorsProvidedCalled = true
        }

        override fun modified(bookId: BookId) {
            fail()
        }
    }

    @Nested
    @DisplayName("when book does not exist")
    inner class TestWhenBookDoesNotExist : ModifyBookPresenter {

        private var bookDoesNotExistCalled = false

        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = setOf(
            Author.create("Elvis Presley"),
            Author.create("Joske Vermeulen")
        )

        @BeforeEach
        fun setUp() {
            modifyBook.modifyBook(
                bookId = BookId.generate(),
                title = title,
                description = description,
                authors = authors,
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(bookDoesNotExistCalled).isTrue()
        }

        @Test
        fun `it should NOT be saved`() {
            bookRepository.verifyEmpty()
        }

        @Test
        fun `it should NOT publish an event`() {
            eventPublisher.verifyNoEventsPublished()
        }

        override fun bookDoesNotExist(bookId: BookId) {
            bookDoesNotExistCalled = true
        }

        override fun noAuthorsProvided() {
            fail()
        }

        override fun modified(bookId: BookId) {
            fail()
        }
    }

    @Nested
    @DisplayName("when book modified successfully")
    inner class TestWhenHappyFlow : ModifyBookPresenter {

        private var bookModifiedCalled = false

        private val bookId = BookId.generate()
        private val isbn = ISBN.create("9781408856772")
        private val title = Title.create("Clean Code")
        private val description = Description.create("Great book about writing better code")
        private val authors = setOf(
            Author.create("Elvis Presley"),
            Author.create("Joske Vermeulen")
        )

        private val newTitle = Title.create("Another Title for the Book")
        private val newDescription = Description.create("Je te flouppe Fli")
        private val newAuthors = setOf(
            Author.create("JK Rowling"),
            Author.create("Joske Vermeulen")
        )

        @BeforeEach
        fun setUp() {
            bookRepository.save(
                Book.fromSnapshot(
                    BookSnapshot(
                        id = bookId,
                        isbn = isbn,
                        title = title,
                        description = description,
                        authors = authors,
                        version = Version.initial()
                    )
                )
            )
            modifyBook.modifyBook(
                bookId = bookId,
                title = newTitle,
                description = newDescription,
                authors = newAuthors,
                presenter = this
            )
        }

        @Test
        fun `it should provide feedback`() {
            assertThat(bookModifiedCalled).isTrue()
        }

        @Test
        fun `it should be saved`() {
            assertThat(bookRepository.getById(bookId)?.takeSnapshot()).isEqualTo(
                BookSnapshot(
                    bookId,
                    isbn,
                    newTitle,
                    newDescription,
                    newAuthors,
                    Version.initial().increment().increment()
                )
            )
        }

        @Test
        fun `it should publish an event`() {
            eventPublisher.verifyLastPublishedEvents(listOf(BookModified(bookId)))
        }

        override fun bookDoesNotExist(bookId: BookId) {
            fail()
        }

        override fun noAuthorsProvided() {
            fail()
        }

        override fun modified(bookId: BookId) {
            bookModifiedCalled = true
        }
    }
}