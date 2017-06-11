package spider

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import spider.bean.UserBean
import spider.bloomFilter.BloomFilter
import spider.util.getDocument
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

/**
 * Created by chenyan on 2017/6/8.
 */
class  SpiderServer{

    // 用户url阻塞队列
    private val urlQuenue: BlockingQueue<String> = LinkedBlockingQueue<String>()
    // 线程池
    private val executor: Executor = Executors.newFixedThreadPool(20)
    // 使用Bloom Filter算法去重
    private val filter: BloomFilter = BloomFilter()

    // 爬取接口
    fun start(start_URL: String): Unit {
        urlQuenue.put(start_URL)
        println(message = "爬取数据开始。。。。。。。。。。。。。。。。。。。。。。。。。。。。。")
        for (it in 0..5){
            executor.execute {
                ->
                thread {
                    ->
                    while (true){
                        val tmp: String = getUrl()
                        if(!filter.contains(tmp)){
                            filter.add(tmp)
                            if(tmp != null){
                                crawler(tmp)
                            }
                        }else{
                            println(message = "此URL已经存在：$tmp")
                        }

                    }
                }
            }
        }
    }

    //爬数据
     private fun crawler(url: String): Unit {
        val userUrlContent: Element = Jsoup.parse(getDocument(url).toString())
        val userContent: String = userUrlContent?.text()
        val bean: UserBean = UserBean()

        bean.run{
            ->
            userUrlContent.run {
                ->
                // 姓名
                name = select(".ProfileHeader-name")?.first()?.text()
                val size: Int = select(".ProfileHeader-infoItem").size
                if(size == 2){
                    select(".ProfileHeader-infoItem")?.first()?.text()?.apply {
                        ->
                        val split = split(" ")
                        for (index in split.indices) {
                            when(index){
                                // 行业
                                0 -> business = split[index]
                                // 公司
                                1 -> company = split[index]
                                // 职位
                                2 -> position = split[index]
                            }
                        }
                    }
                    select(".ProfileHeader-infoItem")?.get(1)?.text()?.apply {
                        ->
                        val split = split(" ")
                        for (index in split.indices) {
                            when(index){
                                // 学校
                                0 -> education = split[index]
                                // 专业
                                1 -> major = split[index]
                            }
                        }
                    }

                    // 性别
                    if(select(".Icon--male")?.html() != ""){
                        sex = "男"
                    }
                    if(select(".Icon--female")?.html() != ""){
                        sex = "女"
                    }

                    // 回答数
                    answersNum = userContent.substring(userContent.indexOf("回答")+2,userContent.indexOf("提问")-1)

                    // 被感谢数
                    starsNum = userContent.substring(userContent.indexOf("获得")+3,userContent.indexOf("次赞同")-1)

                    // 被感谢数
                    thxNum = userContent.substring(userContent.lastIndexOf("获得")+3,userContent.indexOf("次感谢")-1)

                    // 关注的人数
                    followingNum = select(".NumberBoard-value")?.first()?.text()

                    // 被关注人数
                    followersNum = select(".NumberBoard-value")?.get(1)?.text()

                }
            }
        }
        println("爬取成功：" + bean.toString())

    }

    private fun addUserFollowingUrl(url: String): Unit{
        val followingUrl = url + "/following?page=1"
        println(getDocument(followingUrl).toString())
        val followingContent: Element = Jsoup.parse(getDocument(followingUrl).toString())
        followingContent.run {
            ->
            select(".List-item")
                    .map { it.select(".UserLink-link").attr("href") }
                    .filterNot { it.contains("org") }
                    .forEach { urlQuenue.put("https://www.zhihu.com" + it) }
        }
    }

    private fun getUrl(): String {
        val tempUrl: String = urlQuenue.take()
        return tempUrl
    }

}

fun main(args: Array<String>) {

//    SpiderServer().crawler("https://www.zhihu.com/people/chen-yan-78-96/answers")
//      SpiderServer().getUserFollowingUrl("https://www.zhihu.com/people/chen-yan-78-96/following?page=1")

}
