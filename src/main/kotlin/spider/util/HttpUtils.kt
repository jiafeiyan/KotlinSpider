package spider.util

import java.io.File
import java.util.*

/**
 * Created by chenyan on 2017/6/8.
 */
fun setProxyIp(): Unit {
    try {
        val url: String = System.getProperty("user.dir") + "/src/main/kotlin/spider/resources/proxyip.txt"

        val ipList: ArrayList<String> = arrayListOf()

        File(url).readLines(charset = Charsets.UTF_8).forEach {
            line ->
            run {
                ipList.add(line)
            }
        }
        val ip: String = ipList[Random().nextInt(ipList.size)]
        val proxyIp: String = ip.substring(0, ip.lastIndexOf(":"))
        val proxyPort: String = ip.substring(ip.lastIndexOf(":") + 1)

        System.setProperty("http.maxRedirects", "50")
        System.getProperties().setProperty("proxySet", "true")
        System.getProperties().setProperty("http.proxyHost", proxyIp)
        System.getProperties().setProperty("http.proxyPort", proxyPort)

        println(message = "设置代理IP为：$proxyIp , 端口号为：$proxyPort")
    }catch(e: Exception){
        println(message = "出现异常，重新设置代理IP")
        setProxyIp()
    }
}

fun main(args: Array<String>) {
    setProxyIp()
}

