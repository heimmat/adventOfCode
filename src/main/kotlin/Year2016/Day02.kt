package Year2016

import Day

class Day02: Day(2016,2) {

    override fun part1(): Any {

        return input.asList.map { '5'.applyTransformation(it) }.joinToString("")
    }

    override fun part2(): Any {
        return input.asList.runningFold('5') { acc, s ->
            acc.applyTransformation(s, 2)
        }.drop(1).joinToString("")
    }

    private fun Char.applyTransformation(trans: String, part: Int = 1): Char {
        var char = this
        trans.forEach {
            char = char.move(it, part)
        }
        return char
    }

    private fun Char.move(direction: Char, part: Int = 1): Char {
        return if (part == 1) {
            movePart1(direction)
        } else {
            movePart2(direction)
        }
    }

    private fun Char.movePart2(direction: Char): Char {
        if (this !in "123456789ABCD" || direction !in "LRUD") {
            throw IllegalArgumentException()
        } else {
            return when (direction) {
                'L' -> {
                    when (this) {
                        '3' -> '2'
                        '4' -> '3'
                        '6' -> '5'
                        '7' -> '6'
                        '8' -> '7'
                        '9' -> '8'
                        'B' -> 'A'
                        'C' -> 'B'
                        else -> this
                    }
                }
                'R' -> {
                    when (this) {
                        '2' -> '3'
                        '3' -> '4'
                        '5' -> '6'
                        '6' -> '7'
                        '7' -> '8'
                        '8' -> '9'
                        'A' -> 'B'
                        'B' -> 'C'
                        else -> this
                    }
                }
                'U' -> {
                    when (this) {
                        '3' -> '1'
                        in "678" -> this - 4
                        'A' -> '6'
                        'B' -> '7'
                        'C' -> '8'
                        'D' -> 'B'
                        else -> this
                    }
                }
                'D' -> {
                    when (this) {
                        '1' -> '3'
                        in "234" -> this + 4
                        '6' -> 'A'
                        '7' -> 'B'
                        '8' -> 'C'
                        'B' -> 'D'
                        else -> this
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }

    private fun Char.movePart1(direction: Char): Char {
        if (!isDigit() || direction !in "LRUD") {
            throw IllegalArgumentException()
        } else {
            return when(direction) {
                'L' -> {
                    when (this) {
                        '2' -> '1'
                        '3' -> '2'
                        '5' -> '4'
                        '6' -> '5'
                        '8' -> '7'
                        '9' -> '8'
                        else -> this
                    }
                }
                'R' -> {
                    when (this) {
                        '1' -> '2'
                        '2' -> '3'
                        '4' -> '5'
                        '5' -> '6'
                        '7' -> '8'
                        '8' -> '9'
                        else -> this
                    }
                }
                'U' -> {
                    when (this) {
                        in "456789" -> this - 3
                        else -> this
                    }
                }
                'D' -> {
                    when (this) {
                        in "123456" -> this + 3
                        else -> this
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}