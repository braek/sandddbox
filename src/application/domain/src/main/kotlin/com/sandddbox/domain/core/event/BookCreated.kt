package com.sandddbox.domain.core.event

import com.sandddbox.domain.core.EventId
import com.sandddbox.vocabulary.aggregate.BookId
import com.sandddbox.vocabulary.time.ZuluTimestamp

data class BookCreated(
    override val id: EventId,
    override val timestamp: ZuluTimestamp,
    override val streamId: BookId,
) : Event {
    constructor(bookId: BookId) : this(EventId.generate(), ZuluTimestamp.now(), bookId)
}