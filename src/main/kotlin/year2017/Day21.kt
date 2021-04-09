package year2017

import Day

class Day21 : Day(2017,21) {

    val startPattern = listOf(
        ".#.",
        "..#",
        "###"
    ).map { it.toCharArray() }

    override fun part1(): Any {
        val test = listOf(
            listOf(0, 1, 2, 3),
            listOf(4, 5, 6, 7),
            listOf(8, 9, 10, 11),
            listOf(12, 13, 14, 15)
        )
        return test.splitBlocks(2).mergeBlocks()
    }

    private val Char.isOn: Boolean get() = this == '#'
    private val Char.isOff: Boolean get() = !isOn

    fun <T> Collection<Collection<T>>.splitBlocks(size: Int): Collection<Collection<Collection<Collection<T>>>> {
        val rowsOfBlocks = mutableListOf<List<Collection<Collection<T>>>>()
        for (blockY in indices.windowed(size, size)) {
            val blockRow = mutableListOf<Collection<Collection<T>>>()
            for (blockX in this.elementAt(blockY.first()).indices.windowed(size,size)) {
                val block = mutableListOf<Collection<T>>()
                for (elementY in blockY) {
                    block.add(blockX.map { elementX -> this.elementAt(elementY).elementAt(elementX) })
                }
                blockRow.add(block)
            }
            rowsOfBlocks.add(blockRow)
        }
        return rowsOfBlocks
    }

    fun <T> Collection<Collection<Collection<Collection<T>>>>.mergeBlocks(): Collection<Collection<T>> {
        val rows = mutableListOf<Collection<T>>()
        for (blockRow in this) {
            val firstBlockInRow = blockRow.first()
            val test = firstBlockInRow.indices.map { y ->
                blockRow.flatMap { it.elementAt(y) }
            }
            rows.addAll(test)
        }
        return rows
    }

}
