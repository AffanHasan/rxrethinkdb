package at.d4m.rxrethinkdb.query.components

import at.d4m.rxrethinkdb.query.Query
import at.d4m.rxrethinkdb.query.StartingQueryComponent
import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table

/**
 * @author Christoph Muck
 */
class Get internal constructor(private val value: Any) : StartingQueryComponent {
    override fun applyTo(expr: Table): ReqlExpr {
        return expr.get(value)
    }
}

fun Query.Companion.get(value: Any): Query<Table> = this.createQuery(Get(value))