package com.vortexframework.database.credentials

import com.vortexframework.database.DatabaseVendor

class SqliteCredentials(private val file: String): DatabaseCredentials {
    override val databaseVendor = DatabaseVendor.SQLITE
    override val isAuthenticationRequired = false
    override val username = null
    override val password = null

    override fun toJdbcString(): String {
        return "jdbc:sqlite:$file"
    }
}