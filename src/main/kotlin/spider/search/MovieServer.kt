package spider.search

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import spider.bean.MovieBean
import spider.util.getConnection
import spider.util.getDocument
import java.lang.Exception
import java.sql.Connection
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

/**
 * Created by chenyan on 2017/6/13.
 */
class MovieServer{
    // 线程池
    private val executor: Executor = Executors.newFixedThreadPool(5)
    // 电影阻塞队列
    private val urlQuenue: BlockingQueue<String> = LinkedBlockingQueue<String>()
    // 数据库连接
    private val conn: Connection? = getConnection()
    // 根目录
    private val DETAILS_URL: String = "http://www.dy2018.com"



    // 爬取电影查询数据
    public fun crawler(url: String): Unit{
        var url: String = url
        var content: Document = Jsoup.parse(getDocument(url).toString())
        val pages: String = content.body().select(".x").text()
        val page: String = pages.substring(pages.indexOf("页次：") + 3, pages.indexOf("每页") - 1)
        val lastIndex: Int = page.substring(page.indexOf("/")+1).toInt()
        content.run {
            ->
            select(".ulink")
                    .map { it.attr("href") }
                    .forEach { urlQuenue.put(DETAILS_URL+it) }
        }

        for( it in 2..lastIndex){
            url = url.substring(0,url.lastIndexOf("/")+1) + "index_"+it+".html"
            println(message = "开始解析电影列表第$it 页：$url")
            content = Jsoup.parse(getDocument(url).toString())
            content.run {
                ->
                select(".ulink")
                        .map { it.attr("href") }
                        .forEach { urlQuenue.put(DETAILS_URL+it) }
            }
        }
        println(message = "解析完成，一共获取"+urlQuenue.size+"部电影地址。。。。。")

        for (it in 1..5) {
            executor.execute {
                ->
                thread {
                    ->
                    println("启动线程$it。。。。。。")
                    while (urlQuenue.isNotEmpty()){
                        val movieUrl: String = getMovieUrl()
                        crawlerDetails(movieUrl)
                    }
                }
            }
        }
    }
    // 爬取电影详情页数据
    private fun crawlerDetails(url: String): Unit{
        try {

            val content: Document = Jsoup.parse(getDocument(url).toString())
            val movie: MovieBean = MovieBean()
            movie.run {
                ->
                content.run {
                    ->
                    select("#Zoom p").apply {
                        ->
                        //片名
                        title = get(2).text().replace("◎片　　名", "").trim()
                        //译名
                        translated = get(1).text().replace("◎译　　名", "").trim()
                        //年代
                        time = get(3).text().replace("◎年　　代", "").trim()
                        //产地
                        origin = get(4).text().replace("◎国　　家", "").trim()
                        //类别
                        category = get(5).text().replace("◎类　　别", "").trim()
                        //上映时间
                        released = get(8).text().replace("◎上映日期", "").trim()
                        //片长
                        length = get(14).text().replace("◎片　　长", "").trim()
                        //豆瓣评分
                        douban = get(9).text().replace("◎豆瓣评分", "").trim()
                        douban = douban!!.substring(0, douban!!.indexOf("/"))
                        //IMDB评分
                        imdb = get(10).text().replace("◎IMDb评分", "").trim()
                        imdb = imdb!!.substring(0, imdb!!.indexOf("/"))
                    }
                    // 下载地址
                    content.select("tbody a")
                            .map { it.attr("href") }
                            .filter { it.contains(".rmvb") || it.contains(".mkv") }
                            .forEach { if (download != null) download += ";" + it else download = it }
                }
            }
            println(message = "爬取完成：" + movie.title)
            addDataBase(movie)
        }catch (e: Exception){
            println("解析 $url 出现异常.......")
        }
    }

    private fun addDataBase(movie: MovieBean): Unit {
        val sql: String = "Insert Into movie " +
                "(title,translated,time,origin,category,released,length,douban,imdb,download) " +
                "Values (?,?,?,?,?,?,?,?,?,?)"
        conn?.prepareStatement(sql)?.run {
            ->
            movie.run {
                setString(1 , title)
                setString(2 , translated)
                setString(3 , time)
                setString(4 , origin)
                setString(5 , category)
                setString(6 , released)
                setString(7 , length)
                setString(8 , douban)
                setString(9 , imdb)
                setString(10 , download)
                executeUpdate()
            }
        }
    }

    @Synchronized private fun getMovieUrl(): String{
        val url: String = urlQuenue.take()
        println(message = "开始准备爬取电影地址：$url")
        return url
    }
}

fun main(args: Array<String>) {
    MovieServer().crawler("http://www.dy2018.com/html/gndy/dyzz/index.html")
//    MovieServer().crawlerDetails("http://www.dy2018.com/i/97875.html")
}