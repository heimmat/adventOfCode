package Year2015

import Day
import kotlin.text.StringBuilder

class Day10 : Day(2015,10) {

    override fun part1(): String {
        return lookAndSay(40).length.toString()
    }

    override fun part2(): String {
        return lookAndSay(50).length.toString()
    }

    private fun lookAndSay(repeat: Int): String {
        var lookAndSay = input.asString.replace("\n","")
        repeat(repeat) {
            lookAndSay = lookAndSay.lookAndSay()
        }
        return lookAndSay
    }

    private fun String.lookAndSay(): String {
//        val runList = mutableListOf<Pair<Char, Int>>()
//        var working = this
//
//        while (working.isNotEmpty()) {
//            val currentChar = working.first()
//            val chars = working.takeWhile { it == currentChar }
//            working = working.substring(chars.length)
//            runList.add(Pair(currentChar, chars.length))
//        }
//        return runList.joinToString("") {
//            "${it.second}${it.first}"
//        }
        val stringBuilder = StringBuilder()
        var comparingChar = this.first()
        var count = 1
        this.drop(1).forEach {
            if (it == comparingChar) {
                count++
            }
            else {
                stringBuilder.append(count)
                stringBuilder.append(comparingChar)
                comparingChar = it
                count = 1
            }
        }
        stringBuilder.append(count)
        stringBuilder.append(comparingChar)
        return stringBuilder.toString()
    }


}
