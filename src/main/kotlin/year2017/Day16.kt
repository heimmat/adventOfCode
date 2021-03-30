package year2017

import Day

class Day16 : Day(2017,16) {

    private val dancers = ('a'..'p').toList()//mutableListOf('a', 'b', 'c', 'd', 'e')//

    private val moves = input.asString.split(",")

    override fun part1(): Any {
        return executeAllMoves(dancers, moves).joinToString("")
    }

    override fun part2(): Any {
        val loopBetween = detectLoop(dancers, moves)
        return generateSequence(dancers) {
            executeAllMoves(it, moves)
        }.map { it.joinToString("") }.elementAt(1_000_000%loopBetween.second)
    }

    private fun detectLoop(initialDancers: List<Char>, moves: List<String>): Pair<Int,Int> {
        val seenStates = mutableSetOf(initialDancers)
        var newState = executeAllMoves(initialDancers, moves)
        var iterator = 1
        while (newState !in seenStates) {
            seenStates.add(newState)
            newState = executeAllMoves(newState, moves)
            iterator++
        }
        return seenStates.indexOf(newState) to iterator
    }

    private fun executeAllMoves(dancers: List<Char>, moves: List<String>): List<Char>  {
        val mutableDancers = dancers.toMutableList()
        moves.forEach {
            executeDanceMove(mutableDancers, it)
        }
        return mutableDancers
    }

    private fun executeDanceMove(dancers: MutableList<Char>, move: String) {
        when {
            move.startsWith("s") -> dancers.spin(move.drop(1).toInt())
            move.startsWith("x") -> {
                val a = move.substringAfter("x").substringBefore("/").toInt()
                val b = move.substringAfter("/").toInt()
                dancers.exchange(a,b)
            }
            move.startsWith("p") -> {
                val a = move.substringAfter("p").substringBefore("/").first()
                val b = move.substringAfter("/").first()
                dancers.partner(a,b)
            }
            else -> throw IllegalArgumentException()
        }
    }

    private fun MutableList<Char>.partner(a: Char, b: Char) {
        val aIndex = indexOf(a)
        val bIndex = indexOf(b)
        this[aIndex] = b
        this[bIndex] = a
    }

    private fun MutableList<Char>.exchange(a: Int, b: Int) {
        val tmp = this[a]
        this[a] = this[b]
        this[b] = tmp
    }

    private fun MutableList<Char>.spin(size: Int) {
        repeat(size%this.size) {
            spin()
        }
    }

    private fun MutableList<Char>.spin() {
        val last = this.removeLast()
        this.add(0, last)
    }

}
