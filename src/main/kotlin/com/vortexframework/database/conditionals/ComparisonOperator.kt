package com.vortexframework.database.conditionals

enum class ComparisonOperator(private val symbol: String) {
    EQUALS("="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUALS(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    LIKE("LIKE");

    companion object {
        fun find(value: String): ComparisonOperator = ComparisonOperator.values().first { it.symbol == value }
    }
}