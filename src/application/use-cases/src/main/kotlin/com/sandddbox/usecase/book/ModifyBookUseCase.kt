package com.sandddbox.usecase.book

import com.sandddbox.api.ModifyBook
import com.sandddbox.api.ModifyBookPresenter
import com.sandddbox.domain.book.BookRepository
import com.sandddbox.domain.core.EventPublisher
import com.sandddbox.usecase.UseCase
import com.sandddbox.usecase.command.ModifyBookCommand
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.book.Author
import com.sandddbox.vocabulary.book.Description
import com.sandddbox.vocabulary.book.Title

class ModifyBookUseCase(
    private val repository: BookRepository,
    private val eventPublisher: EventPublisher
) : ModifyBook, UseCase<ModifyBookCommand, ModifyBookPresenter> {

    override fun modifyBook(bookId: BookId, title: Title, description: Description, authors: Set<Author>, presenter: ModifyBookPresenter) {
        execute(ModifyBookCommand(bookId, title, description, authors), presenter)
    }

    override fun execute(command: ModifyBookCommand, presenter: ModifyBookPresenter) {
        if (command.authors.isEmpty()) {
            presenter.noAuthorsProvided()
            return
        }
        repository.getById(command.bookId)?.let {
            it.modify(
                title = command.title,
                description = command.description,
                authors = command.authors
            )
            repository.save(it)
            eventPublisher.publish(it.getQueuedEvents())
            presenter.modified(command.bookId)
        } ?: presenter.bookDoesNotExist(command.bookId)
    }
}