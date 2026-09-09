package com.sandddbox.query.model

import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.dto.BookDetails

fun interface GetBookDetailsModel {
    fun getBookDetails(bookId: BookId): BookDetails?
}