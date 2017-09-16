package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.StartingQueryComponent
import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table

/**
 * @author Christoph Muck
 */
data class Insert internal constructor(private val value: Map<String, Any>) : StartingQueryComponent {
    override fun applyTo(expr: Table): ReqlExpr {
        return expr.insert(value)
    }
}

fun Query.Companion.insert(value: Map<String, Any>): Query<Table> = createQuery(Insert(value))