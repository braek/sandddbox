package com.sandddbox.usecase.book

import com.sandddbox.api.CreateBook
import com.sandddbox.api.CreateBookPresenter
import com.sandddbox.domain.book.Book
import com.sandddbox.domain.book.BookRepository
import com.sandddbox.domain.book.ISBNService
import com.sandddbox.domain.core.EventPublisher
import com.sandddbox.usecase.UseCase
import com.sandddbox.usecase.command.CreateBookCommand
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.ISBN
import com.sandddbox.vocabulary.book.Title

class CreateBookUseCase(
    private val isbnService: ISBNService,
    private val repository: BookRepository,
    private val eventPublisher: EventPublisher
) : CreateBook, UseCase<CreateBookCommand, CreateBookPresenter> {

    override fun createBook(isbn: ISBN, title: Title, description: Description, authors: Set<Author>, presenter: CreateBookPresenter) {
        execute(CreateBookCommand(isbn, title, description, authors), presenter)
    }

    override fun execute(command: CreateBookCommand, presenter: CreateBookPresenter) {
        if (command.authors.isEmpty()) {
            presenter.noAuthorsProvided()
            return
        }
        if (isbnService.exists(command.isbn)) {
            presenter.isbnAlreadyInUse(command.isbn)
            return
        }
        val book = Book.create(
            isbn = command.isbn,
            title = command.title,
            description = command.description,
            authors = command.authors
        )
        repository.save(book)
        eventPublisher.publish(book.getQueuedEvents())
        presenter.created(book.getId())
    }
}