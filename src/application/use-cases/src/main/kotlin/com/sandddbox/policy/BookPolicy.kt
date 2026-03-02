package com.sandddbox.policy

import com.sandddbox.domain.core.EventHandler
import com.sandddbox.domain.core.event.BookCreated
import com.sandddbox.domain.core.event.Event

class BookPolicy : EventHandler {
    override fun handle(event: Event) {
        if (event is BookCreated) {

        }
    }
}