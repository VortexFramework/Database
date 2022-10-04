package com.vortexframework.database.entity

import com.vortexframework.database.DatabaseVendor
import com.vortexframework.database.QueryBuilder

interface Table {
    val databaseVendor: DatabaseVendor
    val name: String

    // For ease of table definition
    fun int(name: String) = DatabaseField(name, DatabaseType.INT)
    fun intId(name: String = "id") = DatabaseField(name, DatabaseType.INT)
    fun string(name: String) = DatabaseField(name, DatabaseType.STRING)
    fun boolean(name: String) = DatabaseField(name, DatabaseType.BOOLEAN)

    fun query(init: QueryBuilder.() -> Unit): QueryBuilder {
        val queryBuilder = QueryBuilder(this)
        queryBuilder.init()
        return queryBuilder
    }

    fun toQuery(): String {
        return ""
    }
}