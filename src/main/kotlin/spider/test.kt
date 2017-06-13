package spider

fun main(args: Array<String>) {
    var s = "http://www.dy2018.com/html/gndy/dyzz/index_2.html"
    println(s.substring(0,s.lastIndexOf("/")+1))
}