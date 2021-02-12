package Year2016

import Day

class Day08 : Day(2016,8) {

    val screenWidth = 50
    val screenHeight = 6
    val screen: Map<Pair<Int,Int>, Boolean> = mapOf()

    fun Map<Pair<Int,Int>,Boolean>.print(): String {
        val builder = StringBuilder()
        for (r in 0 until screenHeight) {
            for (c in 0 until screenWidth) {
                builder.append(if (this.getOrDefault(r to c, false)) '#' else '.')
            }
            builder.append('\n')
        }
        return builder.toString()
    }

    fun Map<Pair<Int,Int>,Boolean>.rotateColumn(column: Int, steps: Int): Map<Pair<Int,Int>,Boolean> {
        val mutableCopy = toMutableMap()
        for (r in 0 until screenHeight) {
            mutableCopy[(r + steps)%screenHeight to column] = this.getOrDefault(r to column, false)
        }
        return mutableCopy.filter { it.key.first < screenHeight && it.key.second < screenWidth}
    }

    fun Map<Pair<Int,Int>,Boolean>.rotateRow(row: Int, steps: Int): Map<Pair<Int,Int>,Boolean> {
        val mutableCopy = toMutableMap()
        for (c in 0 until screenWidth) {
            mutableCopy[row to (c + steps)%screenWidth] = this.getOrDefault(row to c, false)
        }

        return mutableCopy.filter { it.key.first < screenHeight && it.key.second < screenWidth}
    }
    fun Map<Pair<Int,Int>,Boolean>.rectangle(column: Int, row: Int): Map<Pair<Int,Int>,Boolean> {
        val mutableCopy = toMutableMap()
        (0 until row).forEach { r ->
            (0 until column).forEach { c ->
                mutableCopy[r to c] = true
            }
        }
        return mutableCopy.filter { it.key.first < screenHeight && it.key.second < screenWidth}
    }



    override fun part1(): Any {
        return runCommands().count { it.value }
    }

    private fun runCommands(): Map<Pair<Int,Int>,Boolean> {
        var screen = screen
        input.asList.forEach {
            when {
                it.startsWith("rect") -> {
                    val (column,row) = it.substring(5).split('x').map { it.toInt() }
                    screen = screen.rectangle(column,row)
                }
                it.startsWith("rotate row") -> {
                    val row = it.substringAfter("y=").substringBefore(" by").toInt()
                    val shift = it.substringAfter("by ").toInt()
                    screen = screen.rotateRow(row,shift)
                }
                it.startsWith("rotate column") -> {
                    val column = it.substringAfter("x=").substringBefore(" by").toInt()
                    val shift = it.substringAfter("by ").toInt()
                    screen = screen.rotateColumn(column,shift)
                }
            }
        }
        return screen
    }

    override fun part2(): Any {
        return "\n${runCommands().print()}"
    }

}
