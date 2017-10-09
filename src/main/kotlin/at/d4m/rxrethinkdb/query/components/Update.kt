package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.QueryComponent
import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table

/**
 * @author Christoph Muck
 */
data class Update internal constructor(private val value: Map<String, Any>) : QueryComponent {
    override fun applyTo(expr: ReqlExpr): ReqlExpr {
        return expr.update(value)
    }
}

fun <T : ReqlExpr> Query<T>.update(value: Map<String, Any>): Query<T> = addComponent(Update(value))
fun Query.Companion.update(value: Map<String, Any>): Query<Table> = createQuery(Update(value))