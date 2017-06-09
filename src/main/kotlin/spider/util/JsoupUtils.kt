package spider.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by chenyan on 2017/6/8.
 */
fun getDocument(url: String): Document {
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
        getDocument(url)
    }
    return getDocument(url)
}

fun main(args: Array<String>) {
//    val url: String = "https://www.zhihu.com/people/chen-yan-78-96/answers"
    val url: String = "https://www.zhihu.com/people/wang-ni-ma-94/answers"
    println(message = getDocument(url))
}