package at.d4m.rxrethinkdb.query

import com.rethinkdb.gen.ast.ReqlExpr
import com.rethinkdb.gen.ast.Table

/**
 * @author Christoph Muck
 */
interface BasicQueryComponent<in T : ReqlExpr> {
    fun applyTo(expr: T): ReqlExpr
}

interface StartingQueryComponent : BasicQueryComponent<Table>
interface QueryComponent : BasicQueryComponent<ReqlExpr>

internal open class DefaultQuery<in T : ReqlExpr> constructor(
        internal val start: BasicQueryComponent<T>? = null,
        internal val rest: MutableList<QueryComponent> = mutableListOf()
) : Query<T> {

    override fun addComponent(queryComponent: QueryComponent): Query<T> {
        rest.add(queryComponent)
        return this
    }

    private fun <E : ReqlExpr> E.apply(exp: BasicQueryComponent<E>): ReqlExpr {
        return exp.applyTo(this)
    }

    override fun construct(firstElement: T): ReqlExpr {
        if (start == null) return firstElement

        var exp = firstElement.apply(start)
        rest.forEach {
            exp = exp.apply(it)
        }
        return exp
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefaultQuery<*>

        if (start != other.start) return false
        if (rest != other.rest) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start?.hashCode() ?: 0
        result = 31 * result + rest.hashCode()
        return result
    }


}

internal class DefaultTableQuery(
        start: BasicQueryComponent<Table>? = null,
        rest: MutableList<QueryComponent> = mutableListOf()
) : TableQuery, DefaultQuery<Table>(start, rest)

interface Query<in T : ReqlExpr> {
    fun construct(firstElement: T): ReqlExpr
    fun addComponent(queryComponent: QueryComponent): Query<T>

    companion object {
        internal fun <T : ReqlExpr> createQuery(component: BasicQueryComponent<T>): Query<T> = DefaultQuery(component)
        fun <T : ReqlExpr> empty(): Query<T> = DefaultQuery()
    }
}


interface TableQuery : Query<Table>

fun test() {
//    val get: Query<Table> = Query.get("").changes()
}