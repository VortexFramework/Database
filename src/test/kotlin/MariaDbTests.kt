import com.vortexframework.database.DatabaseVendor
import com.vortexframework.database.entity.Table
import kotlin.test.Test
import kotlin.test.assertEquals

object TestTable: Table {
    override val databaseVendor = DatabaseVendor.MARIA_DB
    override val name = "test_table"
    val id = intId()
    val username = string("username")
    val isAdmin = boolean("is_admin")
    val age = int("age")
}

class MariaDbTests {
    @Test
    fun `Test can generate basic select query with given arguments`() {
        assertEquals("SELECT `username`, `is_admin` FROM test_table;", TestTable.query {
            select(TestTable.username, TestTable.isAdmin)
        }.toQuery().query)
    }

    @Test
    fun `Test can generate basic where query`() {
        val query = TestTable.query {
            where(TestTable.isAdmin, false)
        }.toQuery()
        assertEquals("SELECT * FROM test_table WHERE is_admin = ?;", query.query)
        assertEquals(false, query.data[0])
    }

    @Test
    fun `Test can generate basic where query with numeric operators`() {
        assertEquals("SELECT * FROM test_table WHERE age > ?;", TestTable.query {
            where(TestTable.age, ">", 0)
        }.toQuery().query)
        assertEquals("SELECT * FROM test_table WHERE age >= ?;", TestTable.query {
            where(TestTable.age, ">=", 0)
        }.toQuery().query)
        assertEquals("SELECT * FROM test_table WHERE age < ?;", TestTable.query {
            where(TestTable.age, "<", 0)
        }.toQuery().query)
        assertEquals("SELECT * FROM test_table WHERE age <= ?;", TestTable.query {
            where(TestTable.age, "<=", 0)
        }.toQuery().query)
        assertEquals("SELECT * FROM test_table WHERE age = ?;", TestTable.query {
            where(TestTable.age, "=", 0)
        }.toQuery().query)
    }

    @Test
    fun `Test can generate string like query`() {
        val query = TestTable.query {
            whereLike(TestTable.username, "%TEST%")
        }.toQuery()
        assertEquals("SELECT * FROM test_table WHERE username LIKE ?;", query.query)
        assertEquals("%TEST%", query.data[0])
    }

    @Test
    fun `Test can generate update query`() {
        val query = TestTable.query {
            update(TestTable.username to "TEST", TestTable.isAdmin to false)
        }.toQuery()
        assertEquals("UPDATE test_table SET `is_admin` = ?, `username` = ?;", query.query)
        assertEquals(false, query.data[0])
        assertEquals("TEST", query.data[1])
    }

    @Test
    fun `Test can generate update query with where clause`() {
        val query = TestTable.query {
            update(TestTable.username to "TEST", TestTable.isAdmin to false)
            where(TestTable.id, 1)
        }.toQuery()
        assertEquals("UPDATE test_table SET `is_admin` = ?, `username` = ? WHERE id = ?;", query.query)
        assertEquals(false, query.data[0])
        assertEquals("TEST", query.data[1])
        assertEquals(1, query.data[2])
    }
}