package spider

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import spider.bean.UserBean
import spider.util.getDocument
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by chenyan on 2017/6/8.
 */
class  SpiderServer{

    var urlQuenue: BlockingQueue<String> = LinkedBlockingQueue<String>()

    //爬数据
     fun crawler(url: String): Unit {
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

    fun getUserFollowingUrl(url: String): Unit{
        var followingUrl = url + "/following?page=1"
        println(getDocument(followingUrl).toString())
        val followingContent: Element = Jsoup.parse(getDocument(followingUrl).toString())
        followingContent.run {
            ->
            for (e in select(".List-item")) {
                println(e.select(".UserLink-link").attr("href"))
            }
        }
    }

}

fun main(args: Array<String>) {
//    SpiderServer().crawler("https://www.zhihu.com/people/chen-yan-78-96/answers")
      SpiderServer().getUserFollowingUrl("https://www.zhihu.com/people/chen-yan-78-96/following?page=1")
}
