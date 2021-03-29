package year2017

import Day

class Day16 : Day(2017,16) {

    val dancers = ('a'..'p').toMutableList()//mutableListOf('a', 'b', 'c', 'd', 'e')//

    val moves = input.asString.split(",")

    override fun part1(): Any {
        moves.forEach {
            executeDanceMove(it)
        }
        return dancers.joinToString("")
    }

    override fun part2(): Any {
        repeat(1_000_000_000) {
            moves.forEach {
                executeDanceMove(it)
            }
        }
        return dancers.joinToString("")
    }

    fun executeDanceMove(move: String) {
        when {
            move.startsWith("s") -> spin(move.drop(1).toInt())
            move.startsWith("x") -> {
                val a = move.substringAfter("x").substringBefore("/").toInt()
                val b = move.substringAfter("/").toInt()
                exchange(a,b)
            }
            move.startsWith("p") -> {
                val a = move.substringAfter("p").substringBefore("/").first()
                val b = move.substringAfter("/").first()
                partner(a,b)
            }
            else -> throw IllegalArgumentException()
        }
    }

    private fun partner(a: Char, b: Char) {
        val aIndex = dancers.indexOf(a)
        val bIndex = dancers.indexOf(b)
        dancers[aIndex] = b
        dancers[bIndex] = a
    }

    private fun exchange(a: Int, b: Int) {
        val tmp = dancers[a]
        dancers[a] = dancers[b]
        dancers[b] = tmp
    }

    private fun spin(size: Int) {
        repeat(size%dancers.size) {
            spin()
        }
    }

    private fun spin() {
        val last = dancers.removeLast()
        dancers.add(0, last)
    }

}
