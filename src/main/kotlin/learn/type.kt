package learn

import kotlin.properties.Delegates


/**
 * Created by chenyan on 2017/5/25.
 */
fun main(args: Array<String>) {
    val asc = Array(5,init = {i->i+2})
//    asc.forEach { print("$it ,") }

    var a = A("abc")
    var result = Address();
    val user = User("chenyan", 24)

}

class A(b:String){
    init {
        print(message = "加载")
    }
}

open class B{
 open fun v(){

 }
}

class C : B(){
   override fun v(){

    }

    fun x(){
       super.v()
    }
}

public sealed class Expr(val map: Map<String, Any?>){
    var bar: String by Delegates.notNull<String>()
}


data class User(val name: String , val age: Int)

public class Address{
    public var name: String = ""
    public var street: String = ""
    fun copyAddress(address: Address):Address{
        var result = Address();
        return result
    }
}





















