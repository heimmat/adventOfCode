package Year2015

import Day

class Day25 : Day(2015,25) {
    val row = input.asString.substringAfter("row ").substringBefore(", column").toInt()
    val column = input.asString.substringAfter("column ").trimEnd().dropLast(1).toInt()

    override fun part1(): String {
        val seq = generateSequence(20151125L) {
            (it * 252533)%33554393
        }
        return seq.takeFromInfinite(row,column).toString()
    }

    fun Sequence<Long>.takeFromInfinite(row: Int, column: Int): Long {
        val diagonalId = row+column
        val rowAtColumnOne = diagonalId - 1
        val sumOfPrevRows = (1 until rowAtColumnOne).sum()
        val index = sumOfPrevRows + column - 1
        return elementAt(index)
    }
}
