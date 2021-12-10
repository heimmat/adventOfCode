package year2021

import Day
import util.Stack
import util.sum

class Day10(debug: Boolean = false): Day(2021,10, debug) {
    private val testInput = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent()

    val chunks = (if (debug) testInput.split("\n") else input.asList)

    fun String.firstIllegalOrNull(): Char? {
        val pairs = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>'
        )
        val stack = Stack<Char>()
        this.forEach {
            if (it in pairs.keys) {
                stack.push(it)
            } else  {
                if (pairs[stack.peek()] == it) {
                    stack.pop()
                } else {
                    return it
                }
            }
        }
        return null
    }
    fun Char.score(): Int {
        return when (this) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalArgumentException()
        }
    }

    override fun part1(): Any {
        return chunks.mapNotNull { it.firstIllegalOrNull() }.map { it.score() }.sumOf { it }
    }

    override fun part2(): Any {
        val listofscores = chunks.filter { it.firstIllegalOrNull() == null }.map { it.complete().score() }.sorted()
        return listofscores[listofscores.size/2]
    }

    fun String.complete(): String {
        val pairs = mapOf(
            '(' to ')',
            '[' to ']',
            '{' to '}',
            '<' to '>'
        )
        val stack = Stack<Char>()
        this.forEach {
            if (it in pairs.keys) {
                stack.push(it)
            } else  {
                if (pairs[stack.peek()] == it) {
                    stack.pop()
                } else {
                   throw IllegalArgumentException()
                }
            }
        }
        val builder = StringBuilder()
        while (stack.isNotEmpty()) {
            builder.append(pairs[stack.pop()])
        }
        return builder.toString()
    }
    fun String.score(): Long {
        return this.fold(0L) { acc, c ->
            acc * 5 + (")]}>".indexOf(c) + 1)
        }
    }
}