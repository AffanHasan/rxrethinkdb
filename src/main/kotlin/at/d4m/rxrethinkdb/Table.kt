package at.d4m.rxrethinkdb

import at.d4m.rxrethinkdb.query.DefaultQueryResponse
import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.QueryResponse
import com.rethinkdb.gen.ast.Db
import com.rethinkdb.net.Connection

interface Table {
    val name: String
    fun executeQuery(query: Query<com.rethinkdb.gen.ast.Table> = Query.empty()): QueryResponse
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

    //Remove response: {deleted=4, inserted=0, unchanged=0, replaced=0, errors=0, skipped=0}
    override fun executeQuery(query: Query<com.rethinkdb.gen.ast.Table>) = DefaultQueryResponse(query.construct(table).run(connection))
}