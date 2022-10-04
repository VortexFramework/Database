package com.vortexframework.database.entity

data class DatabaseField(val name: String, val type: DatabaseType, val flags: ArrayList<String> = ArrayList())
