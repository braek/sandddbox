package com.sandddbox.domain.core

import com.sandddbox.vocabulary.aggregate.AggregateRootId

interface Repository<ID, AGGREGATE_ROOT> where ID : AggregateRootId, AGGREGATE_ROOT : AggregateRoot<ID> {

    fun getById(id: ID): AGGREGATE_ROOT?

    fun save(aggregateRoot: AGGREGATE_ROOT)
}