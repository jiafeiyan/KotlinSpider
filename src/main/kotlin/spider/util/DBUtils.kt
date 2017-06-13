package spider.util

import java.io.File
import java.sql.Connection
import java.sql.DriverManager.getConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

/**
 * Created by chenyan on 2017/6/8.
 * 连接数据库
 * 断开数据库
 */
fun getConnection(): Connection? {
    val conn: Connection
    var driverClassName: String? = null
    var url: String? = null
    var username: String? = null
    var password: String? = null
    val propurl: String = System.getProperty("user.dir")+"/src/main/kotlin/spider/resources/db.properties"

    val pops: Properties = Properties()

    pops.apply {
        ->
        File(propurl).inputStream().use {
            file ->
            load(file).run {
                ->
                driverClassName = getProperty("driverClassName")
                url = getProperty("url")
                username = getProperty("username")
                password = getProperty("password")
            }
        }
    }

    Class.forName(driverClassName)

    return getConnection(url, username, password)
}

fun closeConnection(rs: ResultSet?,
                    pstat: PreparedStatement?,
                    conn: Connection?): Unit {
    rs?.close()
    pstat?.close()
    conn?.close()
}