package com.sandddbox.api

import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.dto.BookDetails

fun interface GetBookDetails {
    fun getBookDetails(bookId: BookId): BookDetails?
}