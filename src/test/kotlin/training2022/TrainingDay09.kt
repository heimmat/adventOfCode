package training2022

import TestDay
import util.Direction
import util.isTouching
import util.moveTowards
import year2022.Day09
import kotlin.test.Test
import util.plus
import kotlin.test.assertEquals

class TrainingDay09: TestDay(Day09(true)) {
    override val results: Pair<Any, Any> = 13 to 1

    val largerExample = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()
        .split("\n")
        .filter { it.isNotEmpty() }
        .map {
            val dir = Direction.parse(it.substringBefore(" "))
            val count = it.substringAfter(" ").toInt()
            dir to count
        }

    @Test
    fun test_larger_example() {
        var rope = buildList<Pair<Int,Int>>(10) {
            repeat(10) {
                this.add(0 to 0)
            }
        }
        val tailPositions = mutableMapOf(
            (0 to 0) to 1
        )
        for (instruction in largerExample) {
            repeat(instruction.second) {
                rope = moveRope(rope, instruction.first)
                val tail = rope.last()
                val positionCount = tailPositions.getOrDefault(tail, 0)
                tailPositions[tail] = positionCount + 1
            }

        }
        println(tailPositions)
        println(rope)
        assertEquals(36, tailPositions.size)

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
    fun Pair<Int,Int>.follow(head: Pair<Int,Int>): Pair<Int,Int> {
        return if (this.isTouching(head)) {
            this
        } else {
            this.moveTowards(head)
        }
    }



}