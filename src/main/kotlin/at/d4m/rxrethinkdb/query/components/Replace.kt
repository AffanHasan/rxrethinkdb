package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.QueryComponent
import at.d4m.rxrethinkdb.query.StartingQueryComponent
import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table

/**
 * @author Christoph Muck
 */
data class Replace internal constructor(private val value: Map<String, Any>) : QueryComponent {
    override fun applyTo(expr: ReqlExpr): ReqlExpr {
        return expr.replace(value)
    }
}

fun <T : ReqlExpr> Query<T>.replace(value: Map<String, Any>): Query<T> = addComponent(Replace(value))
fun Query.Companion.replace(value: Map<String, Any>): Query<Table> = createQuery(Replace(value))