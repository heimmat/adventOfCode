package Year2016

import Day
import java.security.MessageDigest

class Day05: Day(2016,5) {

    override fun part1(): Any {
        val doorId = input.asString.trimEnd()
        val hashes = (1..8).runningFold(0L to "") { acc: Pair<Long, String>, i: Int ->
            findMatchingHash(doorId, acc.first)
        }
        return hashes.drop(1).map { it.second[5] }.joinToString("")
    }

    override fun part2(): Any {
        val doorId = input.asString.trimEnd()
        val password: MutableList<Char?> = (1..8).map { null }.toMutableList()
        var startIndex = 0L
        while (password.contains(null)) {
            val (newStart, hash) = findMatchingHash(doorId, startIndex)
            startIndex = newStart
            val index = hash[5]
            if (index.isDigit()) {
                val digit = index.toString().toInt()
                if (digit in password.indices && password[digit] == null) {
                    password[digit] = hash[6]
                }
            }

        }
        return password.joinToString("")
    }

    private fun findMatchingHash(doorId: String, startIndex: Long, numZeroes: Int = 5): Pair<Long, String> {
        val startsWith = "0".padStart(numZeroes, '0')
        var hash: String
        var iterator = startIndex
        do {
            iterator++
            val toHash = "${doorId}$iterator"
            hash = toHash.md5Hex()
        } while (!hash.startsWith(startsWith))
        return iterator to hash
    }

    fun String.md5Hex(): String = md5(this).toHex()

    //https://stackoverflow.com/a/64172506
    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}

