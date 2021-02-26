package Year2016

import Day
import util.containsRepeatingChar
import util.firstRepeatingChar
import util.md5Hex

//Used https://github.com/tginsberg/advent-2016-kotlin/blob/master/src/main/kotlin/com/ginsberg/advent2016/Day14.kt for debugging
class Day14 : Day(2016,14) {
    private val salt = input.asString.trim()
    private val hashCache = HashCache(salt)

    private fun findKeys(numKeys: Int = 64, nested: Boolean = false, times: Int = 2016): List<Triple<Char,Int,String>> {
        val keys: MutableList<Triple<Char, Int, String>> = mutableListOf()
        val candidates: MutableList<Triple<Char,Int, String>> = mutableListOf()
        var index = 0
        while (keys.size < numKeys) {
            candidates.removeAll {
                it.second + 1000 < index
            }
            val hash = "$salt$index".toMd5Hex(nested, times)

            val secondOccurrence = candidates.filter {
                hash.containsRepeatingChar(it.first,5)
            }
            secondOccurrence.forEach {
                candidates.remove(it)
                keys.add(it)
            }

            val firstOccurrence = hash.firstRepeatingChar(3)
            firstOccurrence?.run {
                candidates.add(Triple(this, index, hash))
            }
            index++
        }
        return keys.take(numKeys)
    }

    private fun findFirstNKeys(n: Int = 64): List<Pair<Int, String>> {
        val keys: MutableList<Pair<Int,String>> = mutableListOf()
        var index = 0
        while (keys.size < n) {
            val hash = hashCache[index]
            val candidateChar = hash.firstRepeatingChar(3)
            if (candidateChar != null) {
                if (hashCache.isKey(index, candidateChar)) {
                    keys.add(index to hash)
                }
            }
            index++
        }
        return keys
    }


    override fun part1(): Any {
        val numKey = 64
        return findKeys(numKey).last().second
        //return findFirstNKeys(numKey).last().first
        //return keys.take(numKey).last().first
    }

    override fun part2(): Any {
        return findKeys(nested = true).last().second
    }

    private val hashes = generateSequence(0 to hashCache[0]) {
        it.first+1 to hashCache[it.first+1]
    }

    private val candidates = hashes.map { Triple(it.first, it.second, it.second.firstRepeatingChar(3)) }.filter { it.third != null }



    val keys = candidates.filter { candidate ->
        hashCache.isKey(candidate.first, candidate.third!!)
    }

    class HashCache(val salt: String) {
        val cache: MutableMap<Int, String> = mutableMapOf()

        private fun toHash(index: Int) = "$salt$index"
        private fun hash(index: Int) = toHash(index).md5Hex()

        operator fun get(index: Int): String {
            if (cache[index] == null) {
                val hash = hash(index)
                cache[index] = hash
                return hash
            } else {
                return cache[index]!!
            }

        }

        fun isKey(index: Int, char: Char): Boolean {
            val range = (index + 1)..(index + 1000)
            return range.map {
                get(it)
            }.any { it.containsRepeatingChar(char, 5) }
        }
    }

    fun String.toMd5Hex(nested: Boolean = false, times: Int = 2016): String {
        return if (nested) {
            (1..times).fold(md5Hex()){ carry, next -> carry.md5Hex() }
        } else {
            md5Hex()
        }
    }

}
