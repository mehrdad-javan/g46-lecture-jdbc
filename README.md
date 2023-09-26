# JDBC Interfaces Cheat Sheet

## DriverManager
- Purpose: Manages a list of database drivers and facilitates database connections.
- Method: `getConnection(url, username, password)` - Establishes a database connection.

## Connection
- Purpose: Represents a connection to the database and is used to create statements.
- Methods:
    - `createStatement()` - Creates a Statement object.
    - `prepareStatement(sql)` - Creates a PreparedStatement object.
    - `commit()` - Commits the current transaction.
    - `rollback()` - Rolls back the current transaction.
    - `setAutoCommit(autoCommit)` - Sets the auto-commit mode.
    - `close()` - Closes the connection.

## Statement
- Purpose: Represents a simple SQL statement without parameters.
- Methods:
    - `executeQuery(sql)` - Executes a SELECT query and returns a ResultSet.
    - `executeUpdate(sql)` - Executes an SQL INSERT, UPDATE, or DELETE statement.
    - `close()` - Closes the statement.

## PreparedStatement
- Purpose: Represents a precompiled SQL statement with placeholders for parameters.
- Methods:
    - `setX(parameterIndex, value)` - Sets parameter values.
    - `executeQuery()` - Executes a SELECT query and returns a ResultSet.
    - `executeUpdate()` - Executes an SQL INSERT, UPDATE, or DELETE statement.
    - `close()` - Closes the PreparedStatement.

## ResultSet
- Purpose: Represents the result set of a SELECT query.
- Methods:
    - `next()` - Moves to the next row in the result set.
    - `getX(columnIndex)` - Retrieves data from the current row.
    - `close()` - Closes the ResultSet.

## CallableStatement
- Purpose: Represents a precompiled SQL statement that can call database stored procedures.
- Methods:
    - `registerOutParameter(parameterIndex, sqlType)` - Registers output parameters.
    - `setX(parameterIndex, value)` - Sets parameter values.
    - `execute()` - Executes the CallableStatement.
    - `getX(index)` - Retrieves output parameter values.

## DataSource
- Purpose: Represents a connection pool that manages database connections.
- Method: `getConnection()` - Gets a database connection from the pool.
