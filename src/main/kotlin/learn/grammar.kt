package learn

import java.util.function.Consumer

/**
 * Created by chenyan on 2017/5/25.
 */
fun main(args: Array<String>) {
    var sum = sum(1, 2)
    println(sum)
    println(message = "My name is $b $")

    val list = mutableListOf<String>()
    list.add(element = "a")
    list.add(element = "b")
    println(message = "hhh ${list[0]}")

    var map: MutableMap<String, String> = mutableMapOf("1" to "ccc")
    val map1 = mapOf<String, String>()
    val hashmap: Cloneable = hashMapOf<String, String>()
    val values = map.entries
    values.forEach(Consumer { println("${it.key} :: ${it.value}") })
    val put: String? = map.put("123", "321")
    println(max(1,2))


}

fun sum(x: Int, y: Int) = x + y

val a: Int = 1
val b: String = "chenyan"

fun max(x: Int , y: Int) = if(x>y) x else y

fun parseInt(str: String): Int? {
    if(str==null){
        return null
    }
    return 0
}

fun getStringLength(str: Any): Int? = if(str is String && str.length > 0) str.length else null

fun case(color: Int = 0): String? =when (color) {
    1 -> {
         "1"
    }
    2 -> {
         "2"
    }
    else -> {
         "3"
    }
}



