package Year2015
import Day
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class Day04 : Day(2015,4) {
    override fun part1(): String {
        return findNumberForMatchingHash(5).toString()
    }

    override fun part2(): String {
        return findNumberForMatchingHash(6).toString()
    }

    private fun calculateMd5(string: String): String {
        return md5(string).toHex()
    }

    private fun findNumberForMatchingHash(zeroes: Int): Long {
        val startsWith = "0".padStart(zeroes, '0')
        var iterator = 0L
        do {
            iterator++
            val toHash = "${input.asString.replace("\n", "")}$iterator"
            val hash = calculateMd5(toHash)
        } while (!hash.startsWith(startsWith))
        return iterator
    }

    //https://stackoverflow.com/a/64172506
    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}
