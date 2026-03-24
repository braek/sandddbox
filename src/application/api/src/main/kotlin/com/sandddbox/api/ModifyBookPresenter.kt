package com.sandddbox.api

import com.sandddbox.vocabulary.aggregate.BookId

interface ModifyBookPresenter {

    fun bookDoesNotExist(bookId: BookId)

    fun noAuthorsProvided()

    fun modified(bookId: BookId)
}