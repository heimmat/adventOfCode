package Year2016

import Day
import util.rotateLeft
import util.rotateRight

class Day21 : Day(2016,21) {

    private val initialString = "abcdefgh"
    private val instructions = input.asList.filterNotEmpty().map {
        Instruction.fromString(it)
    }

    private val testInstructions = listOf(
        Instruction.SwapPosition('4', '0'),
        Instruction.SwapLetter('d', 'b'),
        Instruction.Reverse('0', '4'),
        Instruction.RotateDirection('1', 'l'),
        Instruction.Move('1', '4'),
        Instruction.Move('3', '0'),
        Instruction.RotatePosition('b'),
        Instruction.RotatePosition('d')

    )

    override fun part1(): Any {
        val initialString = "abcde"
        return testInstructions.fold(initialString) { acc, instruction ->
            acc.mutate(instruction)
        }
    }

    override fun part2(): Any {
        return instructions.reversed().fold("fbgdceah") { acc, instruction ->
            instruction.reverse(acc)
        }
    }

    private fun String.mutate(instruction: Instruction): String {
        return instruction.apply(this)
    }

    sealed class Instruction(val x: Char, val y: Char) {
        class SwapPosition(x: Char, y: Char): Instruction(x,y) {
            val xInt = Character.getNumericValue(x)
            val yInt = Character.getNumericValue(y)
            override fun apply(string: String): String {
                val builder = StringBuilder(string)
                val tmp = builder[xInt]
                builder[xInt] = builder[yInt]
                builder[yInt] = tmp
                return builder.toString()
            }

            override fun reverse(string: String): String = apply(string)
        }
        class SwapLetter(x: Char, y: Char): Instruction(x,y) {
            override fun apply(string: String): String {
                return string.replace(x,'?').replace(y, x).replace('?', y)
            }

            override fun reverse(string: String): String = apply(string)
        }
        class RotateDirection(x: Char,y: Char): Instruction(x,y) {
            init {
                if (y !in "lLrR") throw IllegalArgumentException("Argument y $y not in lLrR")
            }
            private val rotations = Character.getNumericValue(x)

            override fun apply(string: String): String {
                return if (y.toLowerCase() == 'r') {
                    string.rotateRight(rotations)
                } else {
                    string.rotateLeft(rotations)
                }
            }

            override fun reverse(string: String): String {
                return if (y.toLowerCase() == 'l') {
                    string.rotateRight(rotations)
                } else {
                    string.rotateLeft(rotations)
                }
            }
        }
        class RotatePosition(x: Char): Instruction(x, '!') {
            override fun apply(string: String): String {
                val indexOfX = string.indexOf(x)
                var rotations = 1 + indexOfX
                if (indexOfX >= 4) rotations++
                return string.rotateRight(rotations)
            }

            override fun reverse(string: String): String {
                return (1..string.length).map {
                    string.rotateLeft(it)
                }.first { apply(it) == string }
            }
        }
        class Reverse(x: Char, y: Char): Instruction(x,y) {
            override fun apply(string: String): String {
                val xInt = Character.getNumericValue(x)
                val yInt = Character.getNumericValue(y)
                val range = xInt..yInt
                val substring = string.substring(range)
                return string.replaceRange(range, substring.reversed())
            }

            override fun reverse(string: String): String = apply(string)
        }
        class Move(x: Char, y: Char): Instruction(x,y) {
            val xInt = Character.getNumericValue(x)
            val yInt = Character.getNumericValue(y)
            override fun apply(string: String): String {
                val builder = StringBuilder(string)
                val moved = string[xInt]
                builder.deleteAt(xInt)
                builder.insert(yInt, moved)
                return builder.toString()
            }

            override fun reverse(string: String): String {
                val builder = StringBuilder(string)
                val moved = string[yInt]
                builder.deleteAt(yInt)
                builder.insert(xInt, moved)
                return builder.toString()
            }
        }

        abstract fun apply(string: String): String
        abstract fun reverse(string: String): String

        override fun toString(): String {
            return "${javaClass.simpleName} x: $x, y: $y"
        }

        companion object {
            fun fromString(command: String): Instruction {
                val split = command.split(" ")
                return when  {
                    command.startsWith("swap position ") -> {
                        val x = split[2].first()
                        val y = split[5].first()
                        SwapPosition(x,y)
                    }
                    command.startsWith("swap letter ") -> {
                        val x = split[2].first()
                        val y = split[5].first()
                        SwapLetter(x,y)
                    }
                    command.startsWith("rotate based") -> {
                        val x = split.last().first()
                        RotatePosition(x)
                    }
                    command.startsWith("rotate") -> {
                        val x = split[2].first()
                        val y = split[1].first()
                        RotateDirection(x,y)
                    }
                    command.startsWith("reverse") -> {
                        val x = split[2].first()
                        val y = split[4].first()
                        Reverse(x,y)
                    }
                    command.startsWith("move") -> {
                        val x = split[2].first()
                        val y = split[5].first()
                        Move(x,y)
                    }
                    else -> {
                        throw IllegalArgumentException("'$command' is not a valid command")
                    }
                }
            }
        }
    }
}
