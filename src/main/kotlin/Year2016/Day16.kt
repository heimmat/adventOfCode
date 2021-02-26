package Year2016

import Day

class Day16 : Day(2016,16) {

    private val initialString = input.asString.trim()

    override fun part1(): Any {
        return initialString.mutateUntilLength(272).checksum()
    }

    override fun part2(): Any {
        return initialString.mutateUntilLength(35651584).checksum()
    }


    private fun String.mutate(): String {
        val a = this
        val b = a.reversed().replace('1','x').replace('0', '1').replace('x','0')
        return "${a}0${b}"
    }

    private fun String.checksum(): String {
        val checksum = windowed(2,2).map { if (it[0] == it[1]) '1' else '0' }.joinToString("")
        return if (checksum.length % 2 == 0) checksum.checksum() else checksum
    }

    private fun String.mutateUntilLength(length: Int): String {
        var tmp = this
        while (tmp.length < length) {
            tmp = tmp.mutate()
        }
        return tmp.take(length)
    }
}
