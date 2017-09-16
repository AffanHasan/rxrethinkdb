package at.d4m.rxrethinkdb.query

import com.rethinkdb.net.Cursor

/**
 * @author Christoph Muck
 */

interface QueryResponse {
    fun responseAsMap(): Map<String, Any>
    fun responseAsOptionalMap(): Map<String, Any>?
    fun responseAsCursor(): Cursor<Map<String, Any>>
}

class DefaultQueryResponse(private val response: Any) : QueryResponse {

    @Suppress("UNCHECKED_CAST")
    override fun responseAsMap(): Map<String, Any> {
        return response as Map<String, Any>
    }

    @Suppress("UNCHECKED_CAST")
    override fun responseAsOptionalMap(): Map<String, Any>? {
        return response as Map<String, Any>?
    }

    @Suppress("UNCHECKED_CAST")
    override fun responseAsCursor(): Cursor<Map<String, Any>> {
        return response as Cursor<Map<String, Any>>
    }
}