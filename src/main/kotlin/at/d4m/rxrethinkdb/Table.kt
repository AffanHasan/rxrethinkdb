package at.d4m.rxrethinkdb

import at.d4m.rxrethinkdb.query.DefaultQuery
import at.d4m.rxrethinkdb.query.Query
import com.rethinkdb.gen.ast.Db
import com.rethinkdb.net.Connection
import com.rethinkdb.net.Cursor

interface Table {
    val name: String
    fun insert(map: Map<String, Any>): Map<String, Any>
    fun removeAll(): Map<String, Any>
    fun find(query: Query<com.rethinkdb.gen.ast.Table> = DefaultQuery()): Cursor<Map<String, Any>>
    fun findOne(query: Query<com.rethinkdb.gen.ast.Table>): Map<String, Any>?
}

/**
 * @author Christoph Muck
 */
internal class DefaultTable constructor(
        override val name: String,
        db: Db,
        private val connection: Connection
) : Table {

    private val table: com.rethinkdb.gen.ast.Table = db.table(name)

    override fun insert(map: Map<String, Any>): Map<String, Any> = table.insert(map).run(connection)
    override fun find(query: Query<com.rethinkdb.gen.ast.Table>): Cursor<Map<String, Any>> = query.construct(table).run(connection)
    override fun findOne(query: Query<com.rethinkdb.gen.ast.Table>): Map<String, Any>? = query.construct(table).run(connection)

    //{deleted=4, inserted=0, unchanged=0, replaced=0, errors=0, skipped=0}
    override fun removeAll(): Map<String, Any> = table.delete().run(connection)
}