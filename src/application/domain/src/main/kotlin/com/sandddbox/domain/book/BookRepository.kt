package com.sandddbox.domain.book

import com.sandddbox.domain.core.Repository
import com.sandddbox.vocabulary.aggregate.BookId

interface BookRepository : Repository<BookId, Book>