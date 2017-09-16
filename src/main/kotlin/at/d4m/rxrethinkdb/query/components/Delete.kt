package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.QueryComponent
import com.rethinkdb.gen.ast.ReqlExpr

class Delete internal constructor() : QueryComponent {

    override fun applyTo(expr: ReqlExpr): ReqlExpr {
        return expr.delete()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

fun <T : ReqlExpr> Query<T>.delete(): Query<T> = addComponent(Delete())
fun <T : ReqlExpr> Query.Companion.delete(): Query<T> = createQuery(Delete())