package com.example.domain

import com.datastax.driver.core.utils.UUIDs
import org.springframework.data.cassandra.core.cql.PrimaryKeyType.*
import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.mapping.Indexed
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

import java.time.LocalDateTime;

@Table("guest_book")
data class GuestBookEntry(
        @PrimaryKeyColumn(type = PARTITIONED, ordinal = 0)
        val name: String,
        val comment: String,
        @PrimaryKeyColumn(type = CLUSTERED, ordinal = 1,  ordering = Ordering.DESCENDING)
        val time: LocalDateTime  = LocalDateTime.now()
)