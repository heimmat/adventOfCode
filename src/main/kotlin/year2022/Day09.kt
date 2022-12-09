package year2022

import Day
import util.Direction
import util.isTouching
import util.moveTowards
import util.plus

class Day09(debug: Boolean = false): Day(2022,9, false) {

    val testInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    val steps = (if (debug) testInput.split("\n") else input.asList)
        .filterNotEmpty()
        .map {
            val dir = Direction.parse(it.substringBefore(" "))
            val count = it.substringAfter(" ").toInt()
            dir to count
        }

    fun move(head: Pair<Int,Int>, tail: Pair<Int,Int>, direction: Direction): Pair<Pair<Int,Int>,Pair<Int,Int>> {
        val head = head + direction.coordinateDiff
//        if (head.isTouching(tail)) {
//            return head to tail
//        } else {
//            return head to tail.moveTowards(head)
//        }
        return head to tail.follow(head)
    }

    fun Pair<Int,Int>.follow(head: Pair<Int,Int>): Pair<Int,Int> {
        return if (this.isTouching(head)) {
            this
        } else {
            this.moveTowards(head)
        }
    }

    override fun part1(): Any {
        var head = 0 to 0
        var tail = head
        val tailPositions = mutableMapOf(
            tail to 1
        )
        for (instruction in steps) {
            repeat(instruction.second) {
                val result = move(head, tail, instruction.first)
                head = result.first
                tail = result.second
                val positionCount = tailPositions.getOrDefault(tail, 0)
                tailPositions[tail] = positionCount + 1
            }
        }
        return tailPositions.size
    }

    override fun part2(): Any {
        var rope = buildList<Pair<Int,Int>>(10) {
            repeat(10) {
                this.add(0 to 0)
            }
        }
        val tailPositions = mutableMapOf(
            (0 to 0) to 1
        )
        for (instruction in steps) {
            repeat(instruction.second) {
                rope = moveRope(rope, instruction.first)
                val tail = rope.last()
                val positionCount = tailPositions.getOrDefault(tail, 0)
                tailPositions[tail] = positionCount + 1
            }

        }
        return tailPositions.size
    }

    fun moveRope(rope: List<Pair<Int,Int>>, direction: Direction): List<Pair<Int,Int>> {
        val mutableRope = rope.toMutableList()
        for (index in mutableRope.indices) {
            mutableRope[index] = if (index == 0) {
                mutableRope[index] + direction.coordinateDiff
            } else {
                mutableRope[index].follow(mutableRope[index-1])
            }
        }
        return mutableRope

    }
}