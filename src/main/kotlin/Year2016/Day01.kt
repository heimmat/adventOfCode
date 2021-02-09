package Year2016

import Day

class Day01(): Day(2016,1) {
    val instructions = input.asString.filterNotWhitespace().split(",").map {
        Instruction(Turn.valueOf(it.take(1)), it.drop(1).toInt())
    }

    override fun part1(): Any {
        var facing = Direction.NORTH
        var position = 0 to 0
        instructions.forEach {
            facing = facing.turn(it.turn)
            val difference = when (facing) {
                Direction.NORTH -> it.steps to 0
                Direction.EAST -> 0 to it.steps
                Direction.SOUTH -> -it.steps to 0
                Direction.WEST -> 0 to -it.steps
            }
            position += difference
        }
        return position.first + position.second
    }

    override fun part2(): Any {
        val position = findFirstTwiceVisited()
        return position.first + position.second
    }

    private fun findFirstTwiceVisited(): Pair<Int,Int> {
        val visitedPositions = mutableListOf<Pair<Int,Int>>(0 to 0)
        var facing = Direction.NORTH
        instructions.forEach {
            facing = facing.turn(it.turn)
            val difference = when (facing) {
                Direction.NORTH -> it.steps to 0
                Direction.EAST -> 0 to it.steps
                Direction.SOUTH -> -it.steps to 0
                Direction.WEST -> 0 to -it.steps
            }
            val newPosition =  visitedPositions.last() + difference
            val range = visitedPositions.last()..newPosition
            for (pos in range.drop(1)) {
                if (pos in visitedPositions) {
                    return pos
                } else {
                    visitedPositions.add(pos)
                }
            }

        }
        println(visitedPositions)
        return visitedPositions.last()
    }


    data class Instruction(val turn: Turn, val steps: Int)

    enum class Turn {
        L,
        R
    }

    fun Direction.turn(turn: Turn): Direction {
        return when (turn) {
            Turn.L -> {
                val index = (Direction.values().size + this.ordinal - 1)%Direction.values().size
                Direction.values().get(index)
            }
            Turn.R -> {
                val index = (this.ordinal + 1)%Direction.values().size
                Direction.values().get(index)
            }

        }
    }

    operator fun Pair<Int,Int>.plus(other: Pair<Int,Int>) = this.first + other.first to this.second + other.second
    operator fun Pair<Int,Int>.rangeTo(that: Pair<Int,Int>): List<Pair<Int,Int>> {
        val mutableList = mutableListOf<Pair<Int,Int>>()
        val firstRange = if (this.first <= that.first) this.first..that.first else this.first downTo that.first
        val secondRange = if (this.second <= that.second) this.second..that.second else this.second downTo that.second
        for (first in firstRange) {
            for (second in secondRange) {
                mutableList.add(first to second)
            }
        }
        return mutableList
    }
}
