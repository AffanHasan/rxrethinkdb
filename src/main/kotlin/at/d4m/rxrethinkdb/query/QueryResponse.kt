package at.d4m.rxrethinkdb.query

import com.rethinkdb.gen.exc.ReqlDriverError
import com.rethinkdb.net.Cursor
import io.reactivex.Emitter
import io.reactivex.Flowable
import org.slf4j.LoggerFactory

/**
 * @author Christoph Muck
 */

interface QueryResponse {
    fun responseAsMap(): Map<String, Any>
    fun responseAsOptionalMap(): Map<String, Any>?
    fun responseAsCursor(): Cursor<Map<String, Any>>
    fun responseAsFlowable(): Flowable<Map<String, Any>>
}

class DefaultQueryResponse(private val response: Any) : QueryResponse {

    companion object {
        val LOG = LoggerFactory.getLogger(DefaultQueryResponse::class.java)!!
    }

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

    override fun responseAsFlowable(): Flowable<Map<String, Any>> {
        val generator = { cursor: Cursor<Map<String, Any>>, emitter: Emitter<Map<String, Any>> ->
            println("Emitter ${Thread.currentThread().name}")
            try {
                if (cursor.hasNext()) {
                    emitter.onNext(cursor.next())
                } else {
                    emitter.onComplete()
                }
            } catch (e: ReqlDriverError) {
                if (e.cause is InterruptedException) {
                    LOG.debug("Cursor '{}' interrupted while waiting for next!", cursor.token, e)
                } else {
                    emitter.onError(e)
                }
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
        return Flowable.generate({ responseAsCursor() }, generator)
    }
}