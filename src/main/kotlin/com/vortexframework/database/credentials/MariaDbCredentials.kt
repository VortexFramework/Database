package com.vortexframework.database.credentials

import com.vortexframework.database.DatabaseVendor

class MariaDbCredentials(private val host: String, private val port: Int, override val username: String,
                         override val password: String, val database: String): DatabaseCredentials {
    override val databaseVendor = DatabaseVendor.MARIA_DB
    override val isAuthenticationRequired = true

    override fun toJdbcString(): String {
        return "jdbc:mariadb://$host:$port/$database"
    }
}