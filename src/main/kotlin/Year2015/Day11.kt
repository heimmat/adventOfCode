package Year2015

import Day
import kotlin.text.StringBuilder

class Day11 : Day(2015,11) {
    override fun part1(): String {
        return input.asString.filter { !it.isWhitespace() }.nextValidPassword
    }

    override fun part2(): String {
        return part1().nextValidPassword
    }

    private val String.nextValidPassword: String get() {
        var nextPassword = this.inc()
        while (!(nextPassword.hasTwoPairs && nextPassword.hasIncreasingStraight && nextPassword.hasNoConfusingLetters)) {
            nextPassword = nextPassword.inc()
        }
        return nextPassword
    }

    private val String.hasIncreasingStraight: Boolean get() {
        var maxCounter = 1
        var counter = 1
        var compChar = first()
        drop(1).forEach {
            if (compChar.toInt() + 1 == it.toInt()) {
                counter++
            } else {
                maxCounter = kotlin.math.max(maxCounter,counter)
                counter = 1
            }
            compChar = it
        }
        return maxCounter >= 3
    }

    private val String.hasNoConfusingLetters: Boolean get() = this.none { it in "iol" }

    private val String.hasTwoPairs: Boolean get() {
        val regex = Regex("(\\w)\\1")
        return regex.findAll(this).count() >= 2
    }

    //Adapted from https://www.reddit.com/r/adventofcode/comments/3wbzyv/day_11_solutions/cy3en0c/?utm_source=reddit&utm_medium=web2x&context=3
    private fun String.inc(): String {
        val builder = StringBuilder(this)
        var iterator = builder.lastIndex
        while (true) {
            if (builder[iterator] != 'z') {
                builder[iterator] = builder[iterator].inc()
                return builder.toString()
            }
            else {
                if (iterator == 0) return builder.toString()

                builder[iterator] = 'a'
                iterator--
            }
        }
    }

    //Can't define an operator fun here https://youtrack.jetbrains.com/issue/KT-24800
    private fun String.increment(): String {

        val overflowsMap = mutableMapOf<Int, Boolean>()

        fun overflowsAt(index: Int): Boolean {
            return if (overflowsMap.containsKey(index)) {
                overflowsMap[index]!!
            } else {
                if (index == this.length) {
                    true
                } else if (index == this.lastIndex) {
                    val overflows = this[index] == 'z'
                    overflowsMap[index] = overflows
                    overflows
                } else {
                    overflowsAt(index+1)
                }
            }

        }

        return mapIndexed { index, c ->
            if (overflowsAt(index+1)) {
                if (c == 'z') 'a' else c.inc()
            } else {
                c
            }
        }.joinToString("")
    }



}


