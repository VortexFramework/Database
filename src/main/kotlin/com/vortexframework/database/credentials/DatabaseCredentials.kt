package com.vortexframework.database.credentials

import com.vortexframework.database.DatabaseVendor

interface DatabaseCredentials {
    val databaseVendor: DatabaseVendor
    val isAuthenticationRequired: Boolean
    val username: String?
    val password: String?
    fun toJdbcString(): String
}