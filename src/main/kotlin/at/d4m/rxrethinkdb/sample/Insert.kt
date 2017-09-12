package at.d4m.rxrethinkdb.sample

/**
 * @author Christoph Muck
 */

fun main(args: Array<String>) {
    val status = db.insert().blockingGet()
    println(status)
    conn.close()
}