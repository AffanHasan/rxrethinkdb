package at.d4m.rxrethinkdb

import com.rethinkdb.RethinkDB.r
import com.rethinkdb.net.Connection
import com.rethinkdb.net.Cursor
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author Christoph Muck
 */

public class ReactiveDB(val conn: Connection) {

    val dbName = "test"
    val tableName = "authors"

    fun doThings() {
        val existingTableNames: List<String> = r.db(dbName).tableList().run(conn)

        if (tableName !in existingTableNames) {
            r.db(dbName).tableCreate(tableName)
        }

//    val cursor: Cursor<Any> = r.table(tableName).run(conn)
//
//    cursor.forEach { println(it) }
    }

    fun insert(): Single<Any> {
        return Single.create<Any> { e ->
            e.onSuccess(r.table(tableName).insert(
                    r.hashMap("name", "#YOLO")
                            .with("address", "TestStraße 587")
            ).run<Any>(conn))
        }.subscribeOn(Schedulers.io())
    }

    fun rxCursor(): Flowable<Any> {
        val generator =
                { s: Cursor<Any>, e: Emitter<Any> ->
                    Unit
                    if (s.hasNext()) {
                        e.onNext(s.next())
                    } else {
                        e.onComplete()
                    }
                    println(Thread.currentThread().name)
                }
        return Flowable.generate(
                { r.table(tableName).changes().run<Cursor<Any>>(conn) },
                generator
        ).subscribeOn(Schedulers.io())
    }
}
