package year2017

import Day
import util.*

class Day21 : Day(2017,21) {

    val startPattern = listOf(
        ".#.",
        "..#",
        "###"
    ).map { it.toList() }

    val testTransformations = listOf(
        "../.# => ##./#../...",
        ".#./..#/### => #..#/..../..../#..#"
    ).map { Transformation(it) }

    val transformations = input.asList.filterNotEmpty().map {
        Transformation(it)
    }

    fun iterate(inputPattern: Collection<Collection<Char>>, transformations: List<Transformation>): Collection<Collection<Char>> {
        val sizeOfPattern = inputPattern.size
        val blocks = when {
            sizeOfPattern % 2 == 0 -> {
                inputPattern.splitBlocks(2)
            }
            sizeOfPattern % 3 == 0 -> {
                inputPattern.splitBlocks(3)
            }
            else -> throw IllegalStateException()
        }
        return blocks.map { row ->
            row.map { block ->
                try {
                    val firstMatchingTransformation = transformations.first { it.matchesAnyOrientation(block) }
                    firstMatchingTransformation.replaceWith
                } catch (e: NoSuchElementException) {
                    println(block.orientations().map {
                        it.joinToString("/") {
                            it.joinToString("")
                        }
                    })
                    throw e
                }

            }
        }.mergeBlocks()
    }

    override fun part1(): Any {
        var pattern: Collection<Collection<Char>> = startPattern
        repeat(5) {
            pattern = iterate(pattern, transformations)
        }
        return pattern.sumBy {
            it.count { it.isOn }
        }
    }

    override fun part2(): Any {
        var pattern: Collection<Collection<Char>> = startPattern
        repeat(18) {
            pattern = iterate(pattern, transformations)
        }
        return pattern.sumBy {
            it.count { it.isOn }
        }
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

    fun Collection<Collection<Char>>.print(): String {
        return joinToString("\n") {
            it.joinToString("")
        }
    }



    class Transformation(ruleString: String) {
        val pattern = ruleString.substringBefore(" => ").split("/").map { it.toList() }
        val replaceWith = ruleString.substringAfter(" => ").split("/").map { it.toList() }

        fun matches(test: Collection<Collection<Char>>) = test == pattern
        fun matchesAnyOrientation(test: Collection<Collection<Char>>): Boolean {
            return test.orientations().any { it == pattern }
        }

        override fun toString(): String {
            return (pattern to replaceWith).toString()
        }
    }

}
