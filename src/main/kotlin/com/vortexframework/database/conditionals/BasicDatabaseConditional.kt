package com.vortexframework.database.conditionals

import com.vortexframework.database.entity.DatabaseField

data class BasicDatabaseConditional(val field: DatabaseField,
                                    val comparisonOperator: ComparisonOperator,
                                    val value: Any,
                                    override val joiner: ConditionalJoiner): DatabaseConditional