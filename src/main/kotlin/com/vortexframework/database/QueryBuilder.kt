package com.vortexframework.database

import com.vortexframework.database.conditionals.BasicDatabaseConditional
import com.vortexframework.database.conditionals.ComparisonOperator
import com.vortexframework.database.conditionals.ConditionalJoiner
import com.vortexframework.database.dialects.MariaDbEncoder
import com.vortexframework.database.dialects.SqliteEncoder
import com.vortexframework.database.entity.DatabaseField
import com.vortexframework.database.entity.Table

class QueryBuilder(private val table: Table) {
    private var selectFields = ArrayList<DatabaseField>()
    private var updateFields = HashMap<DatabaseField, Any>()
    private var insertFields = ArrayList<DatabaseField>()
    private var conditionals = ArrayList<BasicDatabaseConditional>()
    private var queryMode = QueryMode.SELECT


    fun select(vararg fields: DatabaseField) {
        selectFields = fields.toCollection(ArrayList())
    }

    private fun where(databaseField: DatabaseField, comparisonOperator: String, value: Any,
                      conditionalJoiner: ConditionalJoiner): QueryBuilder {
        conditionals.add(BasicDatabaseConditional(databaseField, ComparisonOperator.find(comparisonOperator), value,
            conditionalJoiner))
        return this
    }

    fun where(databaseField: DatabaseField, value: Any = true): QueryBuilder {
        return where(databaseField, "=", value, ConditionalJoiner.AND)
    }

    fun where(databaseField: DatabaseField, comparisonOperator: String, value: Any = true): QueryBuilder {
        return where(databaseField, comparisonOperator, value, ConditionalJoiner.AND)
    }

    fun orWhere(databaseField: DatabaseField, value: Any = true): QueryBuilder {
        return where(databaseField, "=", value, ConditionalJoiner.OR)
    }

    fun orWhere(databaseField: DatabaseField, comparisonOperator: String, value: Any = true): QueryBuilder {
        return where(databaseField, comparisonOperator, value, ConditionalJoiner.OR)
    }

    fun whereLike(databaseField: DatabaseField, value: String): QueryBuilder {
        return where(databaseField, "LIKE", value, ConditionalJoiner.AND)
    }

    fun orWhereLike(databaseField: DatabaseField, value: String): QueryBuilder {
        return where(databaseField, "LIKE", value, ConditionalJoiner.OR)
    }

    fun update(vararg fields: Pair<DatabaseField, Any>): QueryBuilder {
        queryMode = QueryMode.UPDATE
        updateFields.clear()
        updateFields.putAll(fields)
        return this
    }

    fun limit(limit: Int) {

    }

    private fun build(): DatabaseQuery {
        return when (table.databaseVendor) {
            DatabaseVendor.MARIA_DB -> MariaDbEncoder().encodeQuery(table, selectFields, conditionals, updateFields,
                insertFields, queryMode)
            DatabaseVendor.SQLITE -> SqliteEncoder().encodeQuery(table, selectFields, conditionals, updateFields,
                insertFields, queryMode)
        }
    }

    fun toQuery(): DatabaseQuery {
        return build()
    }
}