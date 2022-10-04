package com.vortexframework.database.dialects

import com.vortexframework.database.DatabaseQuery
import com.vortexframework.database.QueryMode
import com.vortexframework.database.conditionals.BasicDatabaseConditional
import com.vortexframework.database.entity.DatabaseField
import com.vortexframework.database.entity.Table

class SqliteEncoder: DatabaseEncoder {
    override fun encodeQuery(
        table: Table,
        selectFields: ArrayList<DatabaseField>,
        conditionals: ArrayList<BasicDatabaseConditional>,
        updateFields: HashMap<DatabaseField, Any>,
        insertFields: ArrayList<DatabaseField>,
        queryMode: QueryMode
    ): DatabaseQuery {
        TODO("Not yet implemented")
    }
}