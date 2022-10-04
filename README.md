# Vortex Database
A Kotlin database ORM, designed for Vortex but able to be used in any projects

❗❗❗WARNING❗❗❗

This project is not close to feature complete, don't use it
## Setup
Firstly install it into your project via `command`

Then you need to initialise your database credentials via
```kotlin
Database.init(MariaDbCredientials(
    host = "localhost",
    port = 3306,
    username = "username",
    password = "password",
    database = "example_db"
))
```

## Multiple database connections
If you're using multiple databases even other multiple vendors you can 
define separate connections to be handled on the fly, default will be
used if no connection is specified in the Table model

```kotlin
Database.init(
    "default" to MariaDbCredientials(
        host = "localhost",
        port = 3306,
        username = "username",
        password = "password",
        database = "example_db"
    ),
    "sqlite" to SqliteCredentials("file.sqlite"),
    "memory" to SqliteCredentials(":memory:")
)
```

## Defining table objects
Vortex Database works by defining table schema within a Table object
which is then used to start queries

```kotlin
object TestTable: Table {
    override val name = "test_table"
    val id = intId()
    val username = string("username")
    val isAdmin = boolean("is_admin")
    val age = int("age")
}
```

`name` refers to the table name in the database while the rest of the 
defined variables are the fields within the table

## How to query
Querying is pretty easy and will be fully documented in time but for
now I'd recommend looking at the database tests.