package com.sandddbox.domain.core

import com.sandddbox.vocabulary.aggregate.AggregateRootId

interface Repository<AGGREGATE_ROOT_ID, AGGREGATE_ROOT> where AGGREGATE_ROOT_ID : AggregateRootId, AGGREGATE_ROOT : AggregateRoot<AGGREGATE_ROOT_ID> {

    fun getById(id: AGGREGATE_ROOT_ID): AGGREGATE_ROOT?

    fun save(aggregateRoot: AGGREGATE_ROOT)
}