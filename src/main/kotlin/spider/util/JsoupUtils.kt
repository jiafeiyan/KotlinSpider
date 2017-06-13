package spider.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by chenyan on 2017/6/8.
 */
fun getDocument(url: String): Document? {
    try{
        val document = Jsoup.connect(url).timeout(1000).get()
        if(document == null || document.toString().trim().equals("")){
            println(message = "IP被拦截，开始更换IP")
            setProxyIp()
            getDocument(url)
        }
        return document
    }catch (e: Exception){
        println(message = "出现连接超时！更换IP继续爬")
        setProxyIp()
//        getDocument(url)
        return null
    }
    return getDocument(url)
}

fun main(args: Array<String>) {
//    val url: String = "https://www.zhihu.com/people/chen-yan-78-96/"
    val url: String = "http://www.dy2018.com/i/97875.html"

    println(message = getDocument(url)?.text())
}