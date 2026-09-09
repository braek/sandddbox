package com.sandddbox.query

import com.sandddbox.api.GetBookDetails
import com.sandddbox.query.model.GetBookDetailsModel
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.dto.BookDetails

data class GetBookDetailsQuery(val model: GetBookDetailsModel) : GetBookDetails {
    override fun getBookDetails(bookId: BookId): BookDetails? {
        return model.getBookDetails(bookId)
    }
}