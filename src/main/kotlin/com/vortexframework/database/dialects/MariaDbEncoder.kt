package com.vortexframework.database.dialects

import com.vortexframework.database.DatabaseQuery
import com.vortexframework.database.QueryMode
import com.vortexframework.database.conditionals.BasicDatabaseConditional
import com.vortexframework.database.conditionals.ComparisonOperator
import com.vortexframework.database.conditionals.ConditionalJoiner
import com.vortexframework.database.entity.DatabaseField
import com.vortexframework.database.entity.Table
import kotlin.text.StringBuilder

class MariaDbEncoder: DatabaseEncoder {
    override fun encodeQuery(
        table: Table,
        selectFields: ArrayList<DatabaseField>,
        conditionals: ArrayList<BasicDatabaseConditional>,
        updateFields: HashMap<DatabaseField, Any>,
        insertFields: ArrayList<DatabaseField>,
        queryMode: QueryMode
    ): DatabaseQuery {
        when (queryMode) {
            QueryMode.SELECT -> {
                val data = ArrayList<Any>()
                val queryString = buildString {
                    append("SELECT ")
                    if (selectFields.isEmpty()) {
                        append("*")
                    } else {
                        append(selectFields.joinToString { "`${it.name}`" })
                    }
                    append(" FROM ${table.name}")

                    if (conditionals.isNotEmpty()) {
                        append(" WHERE ${encodeWhere(conditionals, data)}")
                    }
                    append(";")
                }

                return DatabaseQuery(queryString, data)
            }
            QueryMode.UPDATE -> {
                val data = ArrayList<Any>()
                val queryString = buildString {
                    append("UPDATE ${table.name} SET ${updateFields.map {
                        data.add(it.value)
                        "`${it.key.name}` = ?" }.joinToString(", ")}"
                    )
                    if (conditionals.isNotEmpty()) {
                        append(" WHERE ${encodeWhere(conditionals, data)}")
                    }
                    append(";")
                }
                return DatabaseQuery(queryString, data)
            }
            else -> throw NotImplementedError()
        }
    }

    private fun encodeWhere(conditionals: ArrayList<BasicDatabaseConditional>,
                            data: ArrayList<Any>): String {
        return buildString {
            for ((index, conditional) in conditionals.withIndex()) {
                if (index > 0) {
                    append(when(conditional.joiner) {
                        ConditionalJoiner.AND -> " AND "
                        ConditionalJoiner.OR -> " OR "
                    })
                }
                append("${conditional.field.name} ${when(conditional.comparisonOperator) {
                    ComparisonOperator.EQUALS -> "="
                    ComparisonOperator.GREATER_THAN -> ">"
                    ComparisonOperator.GREATER_THAN_OR_EQUALS -> ">="
                    ComparisonOperator.LESS_THAN -> "<"
                    ComparisonOperator.LESS_THAN_OR_EQUALS -> "<="
                    ComparisonOperator.LIKE -> "LIKE"
                }} ?")
                data.add(conditional.value)
            }
        }
    }
}