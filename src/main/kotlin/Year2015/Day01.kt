package Year2015

import Day

class Day01: Day(2015,1) {
    override fun part1(): String {
        val floor = input.asString.count { it == '(' } - input.asString.count { it == ')'}
        return floor.toString()
    }

    override fun part2(): String {
        var position = 0
        var index = 0
        while (position != -1 && index in input.asString.indices) {
            if (input.asString[index] == '(') {
                position++
            }
            else {
                position--
            }
            index++
        }
        return (index).toString()
    }
}