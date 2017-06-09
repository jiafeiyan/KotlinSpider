package spider.bean

/**
 * Created by chenyan on 2017/6/5.
 */
class UserBean{
    var name: String? = null           // 姓名
    var sex: String? = null            // 性别
    var business: String? = null       // 行业
    var company: String? = null        // 公司
    var position: String? = null       // 职位
    var education: String? = null      // 大学
    var major: String? = null          // 专业
    var answersNum: String? = null     // 回答数量
    var starsNum: String? = null       // 被赞同数
    var thxNum: String? = null         // 被感谢数
    var followingNum: String? = null   // 关注的人
    var followersNum: String? = null   // 关注者数量
    override fun toString(): String {
        return "UserBean(name=$name, sex=$sex, business=$business, company=$company, position=$position, education=$education, major=$major, answersNum=$answersNum, starsNum=$starsNum, thxNum=$thxNum, followingNum=$followingNum, followersNum=$followersNum)"
    }

}

