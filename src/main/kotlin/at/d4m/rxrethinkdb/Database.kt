package at.d4m.rxrethinkdb

import com.rethinkdb.RethinkDB
import com.rethinkdb.gen.ast.Db
import com.rethinkdb.net.Connection

/**
 * @author Christoph Muck
 */
interface Database {
    val name: String
    fun getTableWithName(name: String): Table
    fun getTableNames(): List<String>
    fun createTableWithName(name: String): HashMap<String, String>
}

internal class DefaultDatabase constructor(
        override val name: String,
        private val connection: Connection
) : Database {

    private val db: Db = RethinkDB.r.db(name)

    override fun getTableWithName(name: String) = DefaultTable(name, db, connection)
    override fun getTableNames(): List<String> = db.tableList().run(connection)
    override fun createTableWithName(name: String): HashMap<String, String> = db.tableCreate(name).run(connection)
}