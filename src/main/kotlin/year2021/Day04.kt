package year2021

import Day
import util.x
import util.y

class Day04(debug: Boolean = false): Day(2021,4, debug) {
    private val testInput = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7""".trimIndent()

    private val splitOnEmptyLines = (if (debug) testInput else input.asString).split("\n\n")
    val drawnNumbers = splitOnEmptyLines.first().split(",").map { it.toInt() }
    val bingoTables = splitOnEmptyLines.drop(1).map { BingoTable(it) }

    override fun part1(): Any {
        var tableWon: BingoTable? = null
        var winningDraw: List<Int> = listOf()
        for (took in 1..drawnNumbers.size) {
            val drawn = drawnNumbers.take(took)
            tableWon = bingoTables.firstOrNull { it.bingo(drawn) }
            if (tableWon != null) {
                winningDraw = drawn
                break
            }
        }
        return tableWon!!.score(winningDraw)
    }

    override fun part2(): Any {
        val winners = mutableListOf<BingoTable>()
        val remaining: () -> List<BingoTable> = {
            bingoTables.filterNot { it in winners }
        }
        var lastWinnerAt = 0
        for (took in 1..drawnNumbers.size) {
            val drawn = drawnNumbers.take(took)
            val tablesWon = remaining().filter { it.bingo(drawn) }
            if (tablesWon.isNotEmpty()) {
                winners.addAll(tablesWon)
                if (remaining().isEmpty()) {
                    lastWinnerAt = took
                    break
                }
            }
        }
        return winners.last().score(drawnNumbers.take(lastWinnerAt))
    }


    class BingoTable(descriptor: String) {
        operator fun get(key: Pair<Int,Int>): Int? = table.get(key)
        val table: Map<Pair<Int,Int>, Int> = descriptor.trim()
            .split("\n")
            .mapIndexed { x, row ->
                row.trim().replace(Regex("\\s+"), " ").split(" ").mapIndexed { y, element ->
                    (x to y) to element.trim().toInt()
                }
            }.flatten().toMap()

        fun bingo(drawnCards: List<Int>): Boolean {
            return (0..4).any { column(it).all { it in drawnCards } || row(it).all { it in drawnCards }}
        }

        fun score(winningDraw: List<Int>): Int {
            return table.filter { it.value !in winningDraw }.values.sum() * winningDraw.last()
        }

        private fun column(index: Int): Collection<Int> = table.filter { it.key.y == index }.values
        private fun row(index: Int): Collection<Int> = table.filter { it.key.x == index }.values

        override fun toString(): String {
            return table.toString()
        }
    }
}