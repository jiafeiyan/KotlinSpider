package spider.bloomFilter

import java.util.*

/**
 * Created by chenyan on 2017/5/25.
 * BloomFilter算法，去重策略，提供稳定的去重服务，内存消耗较少
 */

class BloomFilter{

    /* BitSet初始分配2^24个bit */
    private val DEFAULT_SIZE: Int get() = 1 shl 25
    private val bits: BitSet = BitSet(DEFAULT_SIZE)

    /* 哈希函数的种子，一般应取质数 */
    private val SEEDS: IntArray get() = intArrayOf(5,7,11,13,31,37,61)
    /* 哈希函数对象 */
    private val func: Array<SimpleHash?> get() = Array(size = SEEDS.size,init = {
        index -> SimpleHash(DEFAULT_SIZE , SEEDS[index])
    })

    /* 将字符串标记到bits中 */
    @Synchronized fun add(value: String): Unit {
        func.forEach { simpleHash ->
            bits.set(simpleHash!!.hash(value),true)
        }
    }

    /* 判断字符串是否已经被bits标记 */
    fun contains(value: String): Boolean {
        if (value == null) return false
        var ret: Boolean = true
        func.forEach { simpleHash ->
            ret = ret && bits.get(simpleHash!!.hash(value))
        }
        return ret
    }

    /* 哈希函数类 */
    private class SimpleHash( val cap: Int , val seed: Int ){
        /* hash函数，采用简单的加权和hash */
        fun hash(value: String): Int {
            var result: Int = 0
            for (i in value.indices){
                result = seed * result + value[i].toInt()
            }
            return (cap - 1) and result
        }
    }
}