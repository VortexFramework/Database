package com.vortexframework.database

import com.vortexframework.database.credentials.DatabaseCredentials
import java.sql.Connection
import java.sql.DriverManager


object Database {
    private val credentials: HashMap<String, DatabaseCredentials> = hashMapOf()
    private val connections: HashMap<String, Connection> = hashMapOf()

    fun init(databaseCredentials: DatabaseCredentials) {
        init("default" to databaseCredentials)
    }

    fun init(vararg databaseCredentials: Pair<String, DatabaseCredentials>) {
        credentials.clear()
        credentials.putAll(databaseCredentials)
    }

    fun connect(databaseName: String): Connection {
        val databaseCredentials = credentials[databaseName] ?: throw NoSuchElementException()
        val connection: Connection = DriverManager.getConnection(
            databaseCredentials.toJdbcString(),
            databaseCredentials.username, databaseCredentials.password
        )

        connections[databaseName] = connection
        return connection
    }

    fun getConnection(databaseName: String): Connection {
        return connections[databaseName] ?: connect(databaseName)
    }
}