package spider.bloomFilter

import java.util.*

/**
 * Created by chenyan on 2017/5/27.
 */
fun main(args: Array<String>) {
    val URLS: Array<String> = arrayOf(
            "http://www.csdn.net/",
            "http://www.baidu.com/",
            "http://www.google.com.hk",
            "http://www.cnblogs.com/",
            "http://www.zhihu.com/",
            "https://www.shiyanlou.com/",
            "http://www.google.com.hk",
            "https://www.shiyanlou.com/",
            "http://www.csdn.net/"
    )

    val filter = BloomFilter()
        URLS.forEach { url ->
            if ( filter.contains(url) ){
                println(message = "contain : $url")
                return@forEach
            }
            filter.add(url)
            println(message = "add : $url")

        }
}