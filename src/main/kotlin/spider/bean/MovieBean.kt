package spider.bean

/**
 * Created by chenyan on 2017/6/13.
 */
class MovieBean{
    var title: String? = null               //片名
    var translated: String? = null          //译名
    var time: String? = null                //年代
    var origin: String? = null              //产地
    var category: String? = null            //类别
    var released: String? = null            //上映时间
    var length: String? = null              //片长
    var douban: String? = null              //豆瓣评分
    var imdb: String? = null                //IMDB评分
    var download: String? = null            //下载地址
    override fun toString(): String {
        return "MovieBean(title=$title, translated=$translated, time=$time, origin=$origin, category=$category, released=$released, length=$length, douban=$douban, imdb=$imdb, download=$download)"
    }


}
