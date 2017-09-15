package at.d4m.rxrethinkdb

import com.rethinkdb.RethinkDB.r
import com.rethinkdb.net.Connection

/**
 * @author Christoph Muck
 */

interface RethinkDBClient {
    companion object {
        fun create(connection: Connection): RethinkDBClient = DefaultRethinkDBClient(connection)
    }

    fun getDatabaseWithName(name: String): Database
    fun getDatabaseNames(): List<String>
    fun createDatabaseWithName(name: String): Map<String, Any>
}

internal class DefaultRethinkDBClient(private val connection: Connection) : RethinkDBClient {

    override fun getDatabaseWithName(name: String): Database = DefaultDatabase(name, connection)
    override fun getDatabaseNames(): List<String> = r.dbList().run(connection)
    override fun createDatabaseWithName(name: String): Map<String, Any> = r.dbCreate(name).run(connection)
}