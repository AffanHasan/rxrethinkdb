package at.d4m.rxrethinkdb.sample

import at.d4m.rxrethinkdb.ReactiveDB
import com.rethinkdb.RethinkDB.r

/**
 * @author Christoph Muck
 */

val conn = r.connection().connect()
val db = ReactiveDB(conn)

fun main(args: Array<String>) {
    db.doThings()
    db.rxCursor().blockingSubscribe { println(it) }

    conn.close()
}