package spider.bean

/**
 * Created by chenyan on 2017/6/5.
 */
data class UserBean(
        val name: String,           // 姓名
        val sex: Int,               // 性别
        val business: String,       // 行业
        val company: String,        // 公司
        val position: String,       // 职位
        val education: String,      // 大学
        val major: String,          // 专业
        val answersNum: String,     // 回答数量
        val starsNum: String,       // 被赞同数
        val thxNum: String,         // 被感谢数
        val followingNum: String,   // 关注的人
        val followersNum: String    // 关注者数量
)