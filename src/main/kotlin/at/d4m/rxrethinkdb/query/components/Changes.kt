package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.QueryComponent
import com.rethinkdb.gen.ast.ReqlExpr

class Changes internal constructor() : QueryComponent {
    override fun applyTo(expr: ReqlExpr): ReqlExpr {
        return expr.changes()
    }
}

fun <T : ReqlExpr> Query<T>.changes(): Query<T> = addComponent(Changes())
fun <T : ReqlExpr> Query.Companion.changes(): Query<T> = createQuery(Changes())
